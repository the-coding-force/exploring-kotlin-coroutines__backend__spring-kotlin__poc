package the.coding.force.exploring_kotlin_coroutines.controller.handler

import java.time.LocalDate

data class ResponseError(
    val timestamp: LocalDate,
    val status: Int,
    val error: String,
    val message: String,
    val exception: Throwable,
    val path: String
)
