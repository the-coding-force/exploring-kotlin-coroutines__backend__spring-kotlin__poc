package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto

@Service
class UpdateDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    fun update(dataId: Long, body: CreateDataRequest) {
        val updatedData = dataRepository.findById(dataId)
            .map { data ->
                val updatedStatus = body.toDto().toEntity().status
                data.copy(status = updatedStatus)
            }
            .orElseThrow {
                DataNotFoundException("Data with ID $dataId was not found for update")
            }

        dataRepository.save(updatedData)
        logger.info { "UpdateDataService.update: Data with ID ${updatedData.id} was updated" }
    }
}
