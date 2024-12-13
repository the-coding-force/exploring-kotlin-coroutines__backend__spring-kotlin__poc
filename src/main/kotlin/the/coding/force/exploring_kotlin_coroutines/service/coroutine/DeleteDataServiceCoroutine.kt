package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * @author Lucas Terra
 * @param dataRepository repository from `DataEntity`
 * @property delete suspend method to delete a `DataEntity` in coroutine context
 */
@Service
class DeleteDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from suspend controller `delete` to find in the database
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     */
    suspend fun delete(dataId: Long) {
        withContext(Dispatchers.IO) {
            dataRepository.findById(dataId).ifPresentOrElse(
                { data ->
                    dataRepository.deleteById(dataId)
                    logger.info { "DeleteDataService.delete with coroutine: Data with ID ${data.id} was deleted" }
                },
                {
                    throw DataNotFoundException("Data with ID $dataId was not found for deletion")
                }

            )
        }
    }
}
