package statistic.period_time

import java.lang.Exception
import java.util.Calendar
import java.util.GregorianCalendar

interface DateExtractor {

    fun extract(): Long

    class Base(
        private val mInput: String
    ) : DateExtractor {

        override fun extract(): Long {
            return try {
                val splitInput = mInput.split(".")
                GregorianCalendar().apply {
                    set(Calendar.DAY_OF_MONTH, splitInput[0].toInt())
                    set(Calendar.MONTH, splitInput[1].toInt() - 1)
                    set(Calendar.YEAR, splitInput[2].toInt())
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
            } catch (e: Exception) {
                throw WrongTimeFormat()
            }
        }
    }
}