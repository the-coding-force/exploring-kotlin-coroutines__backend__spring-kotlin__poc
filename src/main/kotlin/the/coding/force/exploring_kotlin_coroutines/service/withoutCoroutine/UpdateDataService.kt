package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.UpdateDataDto
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class UpdateDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    fun update(dto: UpdateDataDto) {
        dataRepository.findById(dto.id)
            .map { data ->
                data.copy(status = dto.status.toString())
                    .also { dataRepository.save(it) }
                    .also { logger.info { "UpdateDataService.update: Data with ID ${it.id} was updated, status: ${it.status}" } }
            }
            .orElseThrow { DataNotFoundException("Data with ID ${dto.id} was not found for update") }
    }
}
