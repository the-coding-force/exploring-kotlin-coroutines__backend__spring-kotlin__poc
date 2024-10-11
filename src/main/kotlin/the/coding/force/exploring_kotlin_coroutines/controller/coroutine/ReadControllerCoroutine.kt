package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse
import the.coding.force.exploring_kotlin_coroutines.service.coroutine.ReadDataServiceCoroutine

@RestController
@RequestMapping("/api/coroutine")
class ReadControllerCoroutine(
    private val readDataService: ReadDataServiceCoroutine
) {

    @GetMapping("read/{id}")
    suspend fun read(@PathVariable("id") dataId: Long): ResponseEntity<ReadDataResponse> {
        val data = readDataService.read(dataId)
        return ResponseEntity.ok(data)
    }
}
