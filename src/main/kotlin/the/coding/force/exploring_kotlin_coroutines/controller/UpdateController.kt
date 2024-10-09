package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.service.UpdateDataService

@RestController
@RequestMapping("/api")
class UpdateController(
    private val updateDataService: UpdateDataService
) {
    @PutMapping("/update/{id}")
    fun update(@PathVariable("id") dataId: Long, @RequestBody body: CreateDataRequest): ResponseEntity<Unit> {
        updateDataService.update(dataId, body)
        return ResponseEntity.ok().build()
    }
}
