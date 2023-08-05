package vn.base.mvvm.utils.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateTimeHelper {
    companion object {

        fun timeFormatAgo(timeMillis: Long): String {
            val SECOND_MILLIS = 1000
            val MINUTE_MILLIS = 60 * SECOND_MILLIS
            val HOUR_MILLIS = 60 * MINUTE_MILLIS
            val DAY_MILLIS = 24 * HOUR_MILLIS


            val now = System.currentTimeMillis()

            val diff = now - timeMillis

            return when {
                diff < MINUTE_MILLIS -> "vừa xong"
                diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} phút trước"
                diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} giờ trước"
                else -> "${diff / DAY_MILLIS} ngày trước"
            }

        }

        fun Int.getTimeByFormat(pattern: String = "mm:ss"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this * 1000L)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                this.toString()
            }
        }

        fun Int.getEpochTimeToHour(): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this * 1000L)
                val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                "00:00"
            }
        }

        fun Int.getTimeLess(): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this * 1000L)
                val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                "00:00"
            }
        }

        fun Long.getTimeLess(): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this)
                val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                "00:00"
            }
        }

        fun Long.getTimeStart(pattern: String = "k:mm"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                this.toString()
            }
        }

        /**
         * @param pattern default "dd/MM"
         */
        fun Long.getDayMonth(pattern: String = "dd/MM"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                "__:__"
            }
        }

        /**
         * @param pattern default "dd/MM/yyyy"
         */
        fun Long.getDate(pattern: String = "dd/MM/yyyy"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                " "
            }
        }

        /**
         * @param pattern default "dd/MM"
         */
        fun Long.getEpochDayMonth(pattern: String = "dd/MM"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this * 1000L)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                " "
            }
        }

        /**
         * @param pattern default "dd/MM/yyyy"
         */
        fun Long.getEpochDate(pattern: String = "dd/MM/yyyy"): String {
            return try {
                val cal = Calendar.getInstance()
                cal.timeInMillis = (this * 1000L)
                val formatter = SimpleDateFormat(pattern, Locale.getDefault())
                formatter.format(cal.timeInMillis)
            } catch (e: Exception) {
                " "
            }
        }

        fun getDurationBreakdown(
            milliseconds: Long,
            useDotDivide: Boolean = false,
            showDay: Boolean = true,
            showZero: Boolean = false
        ): String {
            var millis = milliseconds
            if (showDay) {
                val units = if (useDotDivide)
                    arrayOf(":", ":", ":", ":")
                else
                    arrayOf(" ngày ", " giờ ", " phút ", " giây ")

                val values = LongArray(units.size)
                require(millis >= 0) { "Duration must be greater than zero!" }
                values[0] = TimeUnit.MILLISECONDS.toDays(millis)
                millis -= TimeUnit.DAYS.toMillis(values[0])
                values[1] = TimeUnit.MILLISECONDS.toHours(millis)
                millis -= TimeUnit.HOURS.toMillis(values[1])
                values[2] = TimeUnit.MILLISECONDS.toMinutes(millis)
                millis -= TimeUnit.MINUTES.toMillis(values[2])
                values[3] = TimeUnit.MILLISECONDS.toSeconds(millis)
                val sb = StringBuilder(64)
                var startPrinting = true

                for (i in units.indices) {
                    if (startPrinting && values[i] != 0L || !startPrinting || i > 0) {
                        startPrinting = false
                        sb.append(String.format("%02d", values[i]))
                        if (i < 3 || !useDotDivide)
                            sb.append(units[i])
                    }
                }
                return sb.toString()
            } else {
                val units = if (useDotDivide)
                    arrayOf(":", ":", ":")
                else
                    arrayOf(" giờ ", " phút ", " giây ")

                val values = LongArray(units.size)
                require(millis >= 0) { "Duration must be greater than zero!" }
                values[0] = TimeUnit.MILLISECONDS.toHours(millis)
                millis -= TimeUnit.HOURS.toMillis(values[0])
                values[1] = TimeUnit.MILLISECONDS.toMinutes(millis)
                millis -= TimeUnit.MINUTES.toMillis(values[1])
                values[2] = TimeUnit.MILLISECONDS.toSeconds(millis)
                val sb = StringBuilder()
                var startPrinting = true

                for (i in units.indices) {
                    if (showZero) {
                        if (startPrinting || !startPrinting || i > 0) {
                            startPrinting = false
                            sb.append(String.format("%02d", values[i]))
                            if (i < 2 || !useDotDivide)
                                sb.append(units[i])
                        }
                    } else
                        if (startPrinting && values[i] != 0L || !startPrinting || i > 0) {
                            startPrinting = false
                            sb.append(String.format("%02d", values[i]))
                            if (i < 2 || !useDotDivide)
                                sb.append(units[i])
                        }
                }
                return sb.toString()
            }
        }

        fun getDurationBreakdown2(milliseconds: Long): String {
            var millis = milliseconds
            var units = arrayOf(" ngày ", " giờ ", " phút ", " giây ")
            var values = LongArray(units.size)
            require(millis >= 0) { "Duration must be greater than zero!" }
            values[0] = TimeUnit.MILLISECONDS.toDays(millis)
            millis -= TimeUnit.DAYS.toMillis(values[0])
            values[1] = TimeUnit.MILLISECONDS.toHours(millis)
            millis -= TimeUnit.HOURS.toMillis(values[1])
            values[2] = TimeUnit.MILLISECONDS.toMinutes(millis)
            millis -= TimeUnit.MINUTES.toMillis(values[2])
            values[3] = TimeUnit.MILLISECONDS.toSeconds(millis)
            val sb = StringBuilder(64)
            var startPrinting = false
            for (i in units.indices) {
                startPrinting = (!startPrinting && values[i] != 0L)
                if (startPrinting) {
                    sb.append(values[i])
                    sb.append(units[i])
                }
            }
            return sb.toString()
        }
    }


}