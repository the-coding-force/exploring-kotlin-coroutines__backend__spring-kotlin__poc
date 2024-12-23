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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.UpdateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.UpdateDataService
import io.swagger.v3.oas.annotations.parameters.RequestBody as RequestBodySwagger

@RestController
@RequestMapping("/api")
@Tag(name = "without-coroutine", description = "operations outside of a coroutine context")
class UpdateController(
    private val updateDataService: UpdateDataService
) {
    @Operation(
        summary = "update a entity",
        description = "receives a `dataId` as pathVariable and an UpdateDataRequest as requestBody and try to update the data in database"
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
                            "message": "Data with ID x was not found for update",
                            "exceptionClass": "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.DataNotFoundException", 
                            "path": "/api/update/10"
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
                                "possibleErrors": [
                                    {
                                        "timestamp": "2024-12-22T10:13:33.072654006-03:00",
                                        "status": 400,
                                        "error": "BAD_REQUEST",
                                        "message": "JSON parse error: Cannot deserialize value of type `the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum` from String \"??\": not one of the values accepted for Enum class: [DELETED, COMPLETED, TODO]",
                                        "exceptionClass": "org.springframework.http.converter.HttpMessageNotReadableException",
                                        "path": "/api/update/10"
                                    },
                                    {
                                        "timestamp": "2024-12-22T10:13:33.072654006-03:00",
                                        "status": 400,
                                        "error": "BAD_REQUEST",
                                        "message": "JSON parse error: Unrecognized token '??': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')",
                                        "exceptionClass": "org.springframework.http.converter.HttpMessageNotReadableException",
                                        "path": "/api/update/10"
                                    },
                                    {
                                        "timestamp": "2024-12-11T13:19:14.083776445-03:00",
                                        "status": 400,
                                        "error": "BAD_REQUEST",
                                        "message": "Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"??\"",
                                        "exceptionClass": "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException", 
                                        "path": "/api/update/HelloWorld"
                                    }
                                ]
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
                            "path": "/api/update/10"
                           }"""
                        )
                    ]
                )
            ]
        ),
    )
    @PutMapping("/update/{id}")
    fun update(
        @Parameter(
            name = "id",
            description = "Unique identifier of the data to update in the database",
            required = true,
            example = "10",
        )
        @PathVariable("id") dataId: Long,

        // renamed the requestBody reference from swagger to prevent conflicts of the same name
        @RequestBodySwagger(
            description = "DTO (data transfer object) used to update a entity in database",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UpdateDataRequest::class),
                )
            ]
        )
        @RequestBody request: UpdateDataRequest
    ): ResponseEntity<Unit> = updateDataService
        .update(request.toDto(dataId))
        .run { ResponseEntity.ok().build() }
}
