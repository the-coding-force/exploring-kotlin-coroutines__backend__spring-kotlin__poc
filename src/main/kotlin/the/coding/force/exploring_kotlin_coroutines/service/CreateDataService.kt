package the.coding.force.exploring_kotlin_coroutines.service

import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class CreateDataService(
    private val dataRepository: DataRepository
) {
    fun create(dto: DataDto) {
        dataRepository.save(dto.toEntity()).run {
            println("CreateDataService.create, entity saved")
        }
    }
}
