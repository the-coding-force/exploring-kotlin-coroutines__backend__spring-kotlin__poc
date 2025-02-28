package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

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
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.ReadDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
@Tag(name = "coroutine", description = "operations in coroutine context")
class ReadControllerCoroutine(
    private val readDataService: ReadDataServiceCoroutine
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
                            "path": "/api/coroutine/read/10"
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
                            "path": "/api/coroutine/read/HelloWorld"
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
                            "path": "/api/coroutine/read/10"
                           }"""
                        )
                    ]
                )
            ]
        ),
    )
    @GetMapping("read/{id}")
    suspend fun read(
        @Parameter(
            name = "id",
            description = "Unique identifier of the data to get in the database",
            required = true,
            example = "10",
            schema = Schema(type = "long", format = "int64", minimum = "1"),
        )
        @PathVariable("id") dataId: Long
    ): ResponseEntity<ReadDataResponse> {
        val data = readDataService.read(dataId)
        return ResponseEntity.ok(data)
    }
}
