package statistic.period_time

import java.util.*

fun Long.days() = this / 86_400_000L

fun Long.startDayTime() = (this.days() * 86400_000) - TimeZone.getDefault().rawOffset

fun Long.endDayTime() = ((this.days() + 1) * 86400_000) - TimeZone.getDefault().rawOffset