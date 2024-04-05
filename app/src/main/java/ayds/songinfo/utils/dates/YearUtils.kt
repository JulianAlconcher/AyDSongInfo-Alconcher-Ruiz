object YearUtils {
    fun isLeapYear(year: Int): String {
        return if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
            "(leap year)"
        else
            "(not a leap year)"
    }
}
