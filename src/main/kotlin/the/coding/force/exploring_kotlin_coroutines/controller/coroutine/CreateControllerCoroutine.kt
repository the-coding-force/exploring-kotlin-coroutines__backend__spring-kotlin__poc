package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.CreateDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
class CreateControllerCoroutine(
    private val createDataService: CreateDataServiceCoroutine
) {
    @PostMapping("/create")
    suspend fun create(@RequestBody request: CreateDataRequest): ResponseEntity<Unit> {
        createDataService.create(request.toDto())
        return ResponseEntity.ok().build()
    }
}
