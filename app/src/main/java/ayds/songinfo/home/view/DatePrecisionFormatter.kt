package ayds.songinfo.home.view


import YearUtils.isLeapYear
import ayds.songinfo.utils.dates.MonthMapper.getMonthName
import ayds.songinfo.home.model.entities.Song

interface DatePrecisionFormatter {
    fun setDatePrecision(song:Song.SpotifySong):String
}

internal class DatePrecisionFormatterImpl: DatePrecisionFormatter {

    override fun setDatePrecision(song: Song.SpotifySong): String {
        return when (song.releaseDatePrecision){
            "day" -> displayDayPrecision(song.releaseDate)
            "month" -> displayMonthPrecision(song.releaseDate)
            "year" -> displayYearPrecision(song.releaseDate)
            else -> "Precisión desconocida"
        }
    }

    private fun displayDayPrecision(releaseDate: String): String {
        val parts = releaseDate.split("-")
        if (parts.size != 3) {
            return "Fecha inválida"
        }
        val (year, month, day) = parts
        return "$day/$month/$year"
    }
    private fun displayMonthPrecision(releaseDate: String): String {
        val parts = releaseDate.split("-")
        if (parts.size != 2) {
            return "Fecha inválida"
        }
        val (year, month) = parts
        val monthNumber = month.toIntOrNull()
        if (monthNumber == null || monthNumber !in 1..12) {
            return "Fecha inválida"
        }
        val monthName = getMonthName(monthNumber)
        return "$monthName,$year"
    }
    private fun displayYearPrecision(releaseDate: String): String {
        return "$releaseDate  ${isLeapYear(releaseDate.toInt())}"
    }

}

