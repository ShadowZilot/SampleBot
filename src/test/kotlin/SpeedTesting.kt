import logs.Logging

interface SpeedTesting {

    fun test() : Long

    class Base(
        private val mTestingCode: () -> Unit,
        private val mSpeedTestName: String,
        private val mDesiredTime: Long
    ) : SpeedTesting {

        override fun test() : Long {
            val startTime = System.currentTimeMillis()
            mTestingCode.invoke()
            val endTime = System.currentTimeMillis()
            Logging.ConsoleLog.log(
                buildString {
                    appendLine("------------------------------------------------")
                    appendLine(mSpeedTestName)
                    appendLine("Actual time = ${endTime-startTime}")
                    appendLine("Expected time = $mDesiredTime")
                    appendLine("Difference (actual/expected) = ${(endTime-startTime)-mDesiredTime}")
                    appendLine("------------------------------------------------")
                }
            )
            return endTime-startTime
        }
    }
}