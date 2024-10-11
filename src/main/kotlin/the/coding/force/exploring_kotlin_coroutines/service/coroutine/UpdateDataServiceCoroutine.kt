package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.Exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto

@Service
class UpdateDataServiceCoroutine(
    private val dataRepository: DataRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val logger = KotlinLogging.logger { }

    suspend fun update(dataId: Long, createDataRequest: CreateDataRequest) {
        withContext(ioDispatcher) {
            dataRepository.findById(dataId).ifPresentOrElse(
                { data ->
                    val updatedData = data.copy(status = createDataRequest.toDto().toEntity().status)
                    dataRepository.save(updatedData)
                    logger.info { "UpdateDataService.update: Data with ID ${updatedData.id} was updated" }
                },
                {
                    throw DataNotFoundException("Data with ID $dataId was not found for update")
                }
            )
        }
    }
}
