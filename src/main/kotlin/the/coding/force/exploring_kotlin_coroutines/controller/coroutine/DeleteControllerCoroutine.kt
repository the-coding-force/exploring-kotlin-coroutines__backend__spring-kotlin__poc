package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.DeleteDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
class DeleteControllerCoroutine(
    private val deleteDataService: DeleteDataServiceCoroutine
) {

    @DeleteMapping("delete/{id}")
    suspend fun delete(@PathVariable("id") dataId: Long): ResponseEntity<Unit> {
        deleteDataService.delete(dataId)
        return ResponseEntity.ok().build()
    }
}
