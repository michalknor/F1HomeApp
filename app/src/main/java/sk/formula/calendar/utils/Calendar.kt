package sk.formula.calendar.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTimeString: String, pattern: String = "d.M"): String {
    val dateTime = LocalDateTime.parse(dateTimeString)

    val dateFormatter = DateTimeFormatter.ofPattern(pattern)

    return dateTime.format(dateFormatter)
}

fun formatTime(dateTimeString: String, pattern: String = "HH:mm"): String {
    val dateTime = LocalDateTime.parse(dateTimeString)

    val timeFormatter = DateTimeFormatter.ofPattern(pattern)

    return dateTime.format(timeFormatter)
}