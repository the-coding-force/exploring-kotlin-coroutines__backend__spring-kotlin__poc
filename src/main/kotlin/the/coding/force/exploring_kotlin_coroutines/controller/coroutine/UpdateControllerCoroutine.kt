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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.UpdateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.UpdateDataServiceCoroutine
import io.swagger.v3.oas.annotations.parameters.RequestBody as RequestBodySwagger

@RestController
@RequestMapping("/api/coroutine")
@Tag(name = "coroutine", description = "operations in coroutine context")
class UpdateControllerCoroutine(
    private val updateDataService: UpdateDataServiceCoroutine
) {

    @Operation(
        summary = "update a entity",
        description = "received a `dataId` as pathVariable and an UpdateDataRequest as requestBody and try to update the data in database"
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
                            "path": "/api/coroutine/delete/10"
                           }"""
                        )
                    ]
                )
            ]
        )
    )
    @PutMapping("update/{id}")
    suspend fun update(
        @Parameter(
            name = "id",
            description = "Unique identifier of the data to update in the database",
            required = true,
            example = "10",
            schema = Schema(type = "long", format = "int64", minimum = "1"),
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
