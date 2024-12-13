package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

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
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.BulkCreateDataServiceCoroutine
import io.swagger.v3.oas.annotations.parameters.RequestBody as RequestBodySwagger

@RestController
@RequestMapping("/api/coroutine")
@Tag(
    name = "load test endpoints",
    description = "these endpoints are about load test to save several data in database"
)
class BulkCreateControllerCoroutine(
    private val bulkCreateDataService: BulkCreateDataServiceCoroutine
) {
    @Operation(
        summary = "create several data in database on coroutine context",
        description = "receives a `BulkCreateDataRequest` that contains the quantity of data to save in database and the status"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "success"),
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
                            "path": "/api/coroutine/create"
                           }"""
                        )
                    ]
                )
            ]
        )
    )
    @PostMapping("/bulk/create")
    suspend fun create(
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
