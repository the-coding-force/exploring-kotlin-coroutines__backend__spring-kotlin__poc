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
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.CreateDataService
import io.swagger.v3.oas.annotations.parameters.RequestBody as RequestBodySwagger

@RestController
@RequestMapping("/api")
@Tag(name = "without-coroutine", description = "operations outside of a coroutine context")
class CreateController(
    private val createDataService: CreateDataService
) {
    @Operation(
        summary = "create a DataEntity",
        description = "received a `CreateDataRequest` as body and convert to DTO to save in database",
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
                            "exceptionClass": "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.Exception", 
                            "path": "/api/coroutine/create"
                           }"""
                        )
                    ]
                )
            ]
        )
    )
    @PostMapping("/create")
    fun create(
        // renamed the requestBody reference from swagger to prevent conflicts of the same name
        @RequestBodySwagger(
            description = "DTO (data transfer object) used to save a new entity in database",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CreateDataRequest::class),
                )
            ]
        )
        @RequestBody request: CreateDataRequest
    ): ResponseEntity<Unit> = createDataService
        .create(request.toDto())
        .run { ResponseEntity.ok().build() }
}
