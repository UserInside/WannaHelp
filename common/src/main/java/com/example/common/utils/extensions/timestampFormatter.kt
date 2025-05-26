package com.example.common.utils.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun timestampFormatter(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
}