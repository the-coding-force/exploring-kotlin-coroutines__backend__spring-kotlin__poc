package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
import the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine.CreateDataService

@RestController
@RequestMapping("/api")
class CreateController(
    private val createDataService: CreateDataService
) {

    @PostMapping("/create")
    fun create(@RequestBody request: CreateDataRequest): ResponseEntity<Unit> {
        createDataService.create(request.toDto())
        return ResponseEntity.ok().build()
    }
}
