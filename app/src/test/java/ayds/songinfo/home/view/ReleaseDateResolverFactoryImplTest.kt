package ayds.songinfo.home.view

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ReleaseDateResolverFactoryImplTest {

    private val releaseDateResolverFactory: DatePrecisionFormatterFactory =
        DatePrecisionFormatterFactoryImpl()

    @Test
    fun `on day precision should return day precision resolver`() {
        val releaseDateResolverFactory: DatePrecisionFormatterFactory =
            DatePrecisionFormatterFactoryImpl()

        val result = releaseDateResolverFactory.getDatePrecisionFormatter(mockk {
            every { releaseDatePrecision } returns "day"
        })


        assertEquals(result::class.java, DatePrecisionDayFormatter::class.java)
    }

    @Test
    fun `on month precision should return month precision resolver`() {
        val result = releaseDateResolverFactory.getDatePrecisionFormatter(mockk {
            every { releaseDatePrecision } returns "month"
        })

        assertEquals(result::class.java, DatePrecisionMonthFormatter::class.java)
    }

    @Test
    fun `on year precision should return year precision resolver`() {
        val result = releaseDateResolverFactory.getDatePrecisionFormatter(mockk {
            every { releaseDatePrecision } returns "year"
        })

        assertEquals(result::class.java, DatePrecisionYearFormatter::class.java)
    }

}