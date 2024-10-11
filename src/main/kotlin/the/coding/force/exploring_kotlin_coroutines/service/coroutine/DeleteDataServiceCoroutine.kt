package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.Exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class DeleteDataServiceCoroutine(
    private val dataRepository: DataRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val logger = KotlinLogging.logger { }

    suspend fun delete(dataId: Long) {
        withContext(ioDispatcher) {
            dataRepository.findById(dataId).ifPresentOrElse(
                {
                    data ->
                    dataRepository.deleteById(dataId)
                    logger.info { "DeleteDataService.delete: Data with ID ${data.id} was deleted" }
                },
                {
                    throw DataNotFoundException("Data with id $dataId was not found for deletion")
                }

            )
        }
    }
}
