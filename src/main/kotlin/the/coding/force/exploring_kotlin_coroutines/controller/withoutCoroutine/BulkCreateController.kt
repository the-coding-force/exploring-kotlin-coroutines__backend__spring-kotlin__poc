package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.request.BulkCreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.BulkCreateDataService

@RestController
@RequestMapping("/api")
class BulkCreateController(
    private val bulkCreateDataService: BulkCreateDataService
) {

    @PostMapping("/bulk/create")
    fun create(@RequestBody request: BulkCreateDataRequest): ResponseEntity<Unit> {
        bulkCreateDataService.create(request.quantity, request.toDto())
        return ResponseEntity.noContent().build()
    }
}
