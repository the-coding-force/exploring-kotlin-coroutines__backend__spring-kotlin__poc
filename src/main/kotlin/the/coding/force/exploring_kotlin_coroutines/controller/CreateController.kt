package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
import the.coding.force.exploring_kotlin_coroutines.service.CreateDataService

@RestController
@RequestMapping("/api")
class CreateController(
    private val createDataService: CreateDataService
) {

    @PostMapping("api/create")
    fun create(request: CreateDataRequest): ResponseEntity<Unit> {
        createDataService.create(request.toDto())
        return ResponseEntity.ok().build()
    }
}
