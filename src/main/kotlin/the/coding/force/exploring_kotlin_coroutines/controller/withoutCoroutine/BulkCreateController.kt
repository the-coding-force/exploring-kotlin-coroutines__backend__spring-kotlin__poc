package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.BulkCreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.BulkCreateDataService
import io.swagger.v3.oas.annotations.parameters.RequestBody as RequestBodySwagger

@RestController
@RequestMapping("/api")
@Tag(
    name = "load test endpoints",
    description = "these endpoints are about load test to save several data in database"
)
class BulkCreateController(
    private val bulkCreateDataService: BulkCreateDataService
) {
    @Operation(
        summary = "create several data in database outside of a coroutine context",
        description = "receives a `BulkCreateDataRequest` that contains the quantity of data to save in database and the status"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "success"),
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
                                "possibleErrors": [
                                    {
                                        "timestamp": "2024-12-22T10:13:33.072654006-03:00",
                                        "status": 400,
                                        "error": "BAD_REQUEST",
                                        "message": "JSON parse error: Cannot deserialize value of type `the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum` from String \"??\": not one of the values accepted for Enum class: [DELETED, COMPLETED, TODO]",
                                        "exceptionClass": "org.springframework.http.converter.HttpMessageNotReadableException",
                                        "path": "/api/bulk/create"
                                    },
                                    {
                                        "timestamp": "2024-12-22T10:13:33.072654006-03:00",
                                        "status": 400,
                                        "error": "BAD_REQUEST",
                                        "message": "JSON parse error: Unrecognized token '??': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')",
                                        "exceptionClass": "org.springframework.http.converter.HttpMessageNotReadableException",
                                        "path": "/api/bulk/create"
                                    }
                                ]
                            }"""
                        ),
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
                            "exceptionClass": "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.Exceṕtion", 
                            "path": "/api/bulk/create"
                           }"""
                        )
                    ]
                )
            ]
        )
    )
    @PostMapping("/bulk/create")
    fun create(
        @RequestBodySwagger(
            description = "DTO (data transfer object) used to save a new entity in database",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BulkCreateDataRequest::class),
                )
            ]
        )
        @RequestBody request: BulkCreateDataRequest
    ): ResponseEntity<Unit> {
        bulkCreateDataService.create(request.quantity, request.toDto())
        return ResponseEntity.noContent().build()
    }
}
