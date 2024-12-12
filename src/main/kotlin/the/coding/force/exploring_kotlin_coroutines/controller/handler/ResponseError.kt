package the.coding.force.exploring_kotlin_coroutines.controller.handler

import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime
@Schema(description = "An error model object to send case an expect or unexpected error occurred")
data class ResponseError(
    @Schema(
        description = "the time that occurred the exception with time zone",
        example = "2024-12-11T13:19:14.083776445-03:00",
    )
    val timestamp: ZonedDateTime,

    @Schema(
        description = "the status code that references the exception threw",
        example = "404"
    )
    val status: Int,

    @Schema(
        description = "the name of status that references the exception threw",
        example = "NOT_FOUND"
    )
    val error: String,

    @Schema(
        description = "the message that is threw in exception",
        example = "Data with ID x was not found to retrieve it"
    )
    val message: String,

    @Schema(
        description = "the class of exception that is threw",
        example = "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.DataNotFoundException"
    )
    val exceptionClass: String,

    @Schema(
        description = "the path of controller that was called to send the request",
        example = "/api/coroutine/delete/10"
    )
    val path: String
)
