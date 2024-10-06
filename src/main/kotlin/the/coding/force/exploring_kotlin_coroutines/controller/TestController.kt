package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
import the.coding.force.exploring_kotlin_coroutines.service.CreateDataService

@RestController
class TestController(
    private val createDataService: CreateDataService
) {

    @PostMapping("/create")
    fun create(request: CreateDataRequest) = createDataService.create(request.toDto())
}
