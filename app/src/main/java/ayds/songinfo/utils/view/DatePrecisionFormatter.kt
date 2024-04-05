package ayds.songinfo.utils.view

import ayds.songinfo.home.model.entities.Song
import java.text.SimpleDateFormat
import java.util.Locale


interface DatePrecisionFormatter {
    fun setDatePrecision(song:Song.SpotifySong):String
}

internal class DatePrecisionFormatterImpl: DatePrecisionFormatter{

    override fun setDatePrecision(song: Song.SpotifySong): String {
        return when (song.releaseDatePrecision){
            "day" -> displayDayPrecision(song.releaseDatePrecision)
            "month" -> displayMonthPrecision(song.releaseDatePrecision)
            "year" -> displayYearPrecision(song.releaseDatePrecision)
            else -> "Precisión desconocida"
        }
    }

    private fun displayDayPrecision(releaseDate: String):String {
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(releaseDate)
        return ("Release date: ${formattedDate?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                it
            )
        }}")
    }

    private fun displayMonthPrecision(releaseDate: String): String {
        return "h"
    }
    private fun displayYearPrecision(releaseDate: String): String {
        return "h"
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

}

