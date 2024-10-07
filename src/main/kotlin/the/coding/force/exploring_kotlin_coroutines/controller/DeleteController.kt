package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.service.DeleteDataService

@RestController
@RequestMapping("/api")
class DeleteController(
    private val deleteDataService: DeleteDataService
) {
    fun delete(dataId: Long): ResponseEntity<Unit> {
        deleteDataService.delete(dataId)
        return ResponseEntity.ok().build()
    }

}