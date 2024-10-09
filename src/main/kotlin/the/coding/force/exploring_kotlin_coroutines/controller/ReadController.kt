package the.coding.force.exploring_kotlin_coroutines.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse
import the.coding.force.exploring_kotlin_coroutines.service.ReadDataService

@RestController
@RequestMapping("/api")
class ReadController(
    private val readDataService: ReadDataService
) {
    @GetMapping("/read/{id}")
    fun read(@PathVariable("id") dataId: Long): ResponseEntity<ReadDataResponse> {
        val response = readDataService.read(dataId)
        return ResponseEntity.ok(response)
    }
}
