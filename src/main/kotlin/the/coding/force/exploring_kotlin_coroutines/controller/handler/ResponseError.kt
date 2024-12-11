package the.coding.force.exploring_kotlin_coroutines.controller.handler

import java.time.ZonedDateTime

data class ResponseError(
    val timestamp: ZonedDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val exceptionClass: String,
    val path: String
)
