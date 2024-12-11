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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.DeleteDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
@Tag(name = "coroutine", description = "operations in coroutine context")
class DeleteControllerCoroutine(
    private val deleteDataService: DeleteDataServiceCoroutine
) {

    @Operation(
        summary = "delete a entity",
        description = "received a `dataId` as pathVariable and try to remove the data in database"
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
                            "message": "Data with ID x was not found for deletion",
                            "exceptionClass": "the.coding.force.exploring_kotlin_coroutines.controller.coroutine.DataNotFoundException", 
                            "path": "/api/coroutine/delete/10"
                           }"""
                        )
                    ]
                )
            ]
        )
    )
    @DeleteMapping("delete/{id}")
    suspend fun delete(
        @Parameter(
            name = "id",
            description = "Unique identifier of the data to delete in the database",
            required = true,
            example = "10",
            schema = Schema(type = "long", format = "int64", minimum = "1")
        )
        @PathVariable("id") dataId: Long
    ): ResponseEntity<Unit> {
        deleteDataService.delete(dataId)
        return ResponseEntity.ok().build()
    }
}
