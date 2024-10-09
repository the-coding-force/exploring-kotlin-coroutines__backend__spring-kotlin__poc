package the.coding.force.exploring_kotlin_coroutines.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
@Service
class CreateDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    fun create(dto: DataDto) {
        dataRepository.save(dto.toEntity()).run {
            logger.info { "CreateDataService.create, entity saved" }
        }
    }
}
