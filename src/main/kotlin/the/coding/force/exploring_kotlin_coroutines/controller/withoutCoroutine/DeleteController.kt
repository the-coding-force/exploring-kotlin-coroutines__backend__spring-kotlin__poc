package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.DeleteDataService

@RestController
@RequestMapping("/api")
class DeleteController(
    private val deleteDataService: DeleteDataService
) {
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") dataId: Long): ResponseEntity<Unit> {
        deleteDataService.delete(dataId)
        return ResponseEntity.ok().build()
    }
}
