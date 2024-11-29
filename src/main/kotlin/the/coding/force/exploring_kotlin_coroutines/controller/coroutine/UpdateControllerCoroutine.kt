package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.UpdateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.UpdateDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
class UpdateControllerCoroutine(
    private val updateDataService: UpdateDataServiceCoroutine
) {
    @PutMapping("update/{id}")
    suspend fun update(
        @PathVariable("id") dataId: Long,
        @RequestBody request: UpdateDataRequest
    ): ResponseEntity<Unit> = updateDataService
        .update(request.toDto(dataId))
        .run { ResponseEntity.ok().build() }
}
