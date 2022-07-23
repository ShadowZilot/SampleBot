package logs

import java.text.SimpleDateFormat
import java.util.*

interface Logging {

    fun log(message: String)

    object ConsoleLog : Logging {
        private val mDateFormat = SimpleDateFormat("dd.LL.yyyy")
        private val mTimeFormat = SimpleDateFormat("h:mm:ss")

        override fun log(message: String) {
            val time = Date(System.currentTimeMillis())
            println("${mDateFormat.format(time)} ${mTimeFormat.format(time)} $message")
        }
    }
}