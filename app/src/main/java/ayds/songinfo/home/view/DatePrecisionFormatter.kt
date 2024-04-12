package ayds.songinfo.home.view
import ayds.songinfo.home.model.entities.Song

    interface DatePrecisionFormatterFactory {
        fun getDatePrecisionFormatter(song: Song.SpotifySong): DatePrecisionFormatter
    }
    class DatePrecisionFormatterFactoryImpl: DatePrecisionFormatterFactory {
        override fun getDatePrecisionFormatter(song: Song.SpotifySong): DatePrecisionFormatter =
            when (song.releaseDatePrecision) {
                "day" -> DatePrecisionDayFormatter(song)
                "month" -> DatePrecisionMonthFormatter(song)
                "year" -> DatePrecisionYearFormatter(song)
                else -> DatePrecisionDefaultFormatter(song)
            }
    }
    interface DatePrecisionFormatter {
        val song: Song.SpotifySong
        fun getReleaseDate(): String
    }
    internal class DatePrecisionDayFormatter(override val song:Song.SpotifySong): DatePrecisionFormatter {
        override fun getReleaseDate(): String {
            val parts = song.releaseDate.split("-")
            if (parts.size != 3) {
                return "Fecha inválida"
            }
            val (year, month, day) = parts
            return "$day/$month/$year"
        }

    }
    internal class DatePrecisionMonthFormatter(override val song:Song.SpotifySong): DatePrecisionFormatter {
        override fun getReleaseDate(): String {
            val parts = song.releaseDate.split("-")
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
        private fun getMonthName(monthNumber: Int): String {
            return when (monthNumber) {
                1 -> "January"
                2 -> "February"
                3 -> "March"
                4 -> "April"
                5 -> "May"
                6 -> "June"
                7 -> "July"
                8 -> "August"
                9 -> "September"
                10 -> "October"
                11 -> "November"
                12 -> "December"
                else -> "Invalid month"
            }
        }

    }

    internal class DatePrecisionYearFormatter(override val song:Song.SpotifySong): DatePrecisionFormatter {
        override fun getReleaseDate(): String {
            val releaseDate = song.releaseDate
            return "$releaseDate ${isLeapYear(song.releaseDate.toInt())}"
        }

        private fun isLeapYear(year: Int): String {
            return if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
                "(leap year)"
            else
                "(not a leap year)"
        }

    }

    internal class DatePrecisionDefaultFormatter(override val song: Song.SpotifySong) :DatePrecisionFormatter {
        override fun getReleaseDate() = song.releaseDate
    }

