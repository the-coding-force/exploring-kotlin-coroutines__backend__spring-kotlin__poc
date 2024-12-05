package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * This Service has the suspend method `delete` inside coroutine context to delete a `DataEntity`
 *
 * @author Lucas Terra
 * @param dataRepository repository from `DataEntity`
 * @property delete suspend method to delete a `DataEntity` in coroutine context
 */
@Service
class DeleteDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    // logger to register the status of suspend method delete
    private val logger = KotlinLogging.logger { }

    /**
     * Received a `dataId` from suspend controller `delete` and try to find the data to delete it,
     * if the data exists it will be deleted in the database, case the data does not exist an exception
     * `DataNotFoundException` will be thrown.
     * The logger will register the status of delete operation
     *
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from suspend controller `delete` to find in the database
     * @return `Unit`
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     * @see logger
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
