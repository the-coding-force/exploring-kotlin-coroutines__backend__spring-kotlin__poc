package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.BulkCreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.BulkCreateDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
class BulkCreateControllerCoroutine(
    private val bulkCreateDataService: BulkCreateDataServiceCoroutine
) {
    @PostMapping("/bulk/create")
    suspend fun create(@RequestBody request: BulkCreateDataRequest): ResponseEntity<Unit> {
        bulkCreateDataService.create(request.quantity, request.toDto())
        return ResponseEntity.noContent().build()
    }
}
