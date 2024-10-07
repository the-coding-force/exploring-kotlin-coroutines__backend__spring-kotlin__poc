package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.UpdateDataService

@RestController
@RequestMapping("/api")
class UpdateController(
    private val updateDataService: UpdateDataService
) {
    fun update(dataId: Long, body: CreateDataRequest): ResponseEntity<Unit> {
        updateDataService.update(dataId, body)
        return ResponseEntity.ok().build()
    }

}