package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.ReadDataService

@RestController
@RequestMapping("/api")
@Tag(name = "without-coroutine", description = "operations outside of a coroutine context")
class ReadController(
    private val readDataService: ReadDataService
) {
    @Operation(
        summary = "get a entity",
        description = "receives a `dataId` as pathVariable and try to get the data in database"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "success"),
        ApiResponse(
            responseCode = "404",
            description = "not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseError::class),
                    examples = [
                        ExampleObject(
                            value = """{
                            "timestamp": "2024-12-11T13:19:14.083776445-03:00",
                            "status": 404,
                            "error": "NOT_FOUND",
                            "message": "Data with ID x was not found to retrieve it",
                            "exceptionClass": "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.DataNotFoundException", 
                            "path": "/api/read/10"
                           }"""
                        )
                    ]
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "bad request",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseError::class),
                    examples = [
                        ExampleObject(
                            value = """{
                            "timestamp": "2024-12-11T13:19:14.083776445-03:00",
                            "status": 400,
                            "error": "BAD_REQUEST",
                            "message": "Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"??\"",
                            "exceptionClass": "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException", 
                            "path": "/api/read/HelloWorld"
                           }"""
                        )
                    ]
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "internal server error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseError::class),
                    examples = [
                        ExampleObject(
                            value = """{
                            "timestamp": "2023-12-06T12:34:56",
                            "status": 500,
                            "error": "Internal Server Error",
                            "message": "Unexpected error occurred.",
                            "path": "/api/read/10"
                           }"""
                        )
                    ]
                )
            ]
        ),
    )
    @GetMapping("/read/{id}")
    fun read(
        @Parameter(
            name = "id",
            description = "Unique identifier of the data to get in the database",
            required = true,
            example = "10",
        )
        @PathVariable("id") dataId: Long
    ): ResponseEntity<ReadDataResponse> {
        val response = readDataService.read(dataId)
        return ResponseEntity.ok(response)
    }
}
