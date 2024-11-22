package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * Service responsible for deleting data entities in a coroutine context.
 *
 * This service provides a coroutine-based method to delete data entities in the repository.
 * A logger is used to track the status of the operation.
 *
 * @author Lucas Terra
 * @property dataRepository the repository used to interact with the data source.
 */

@Service
class DeleteDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * Deletes a data entity by its ID in a coroutine context.
     *
     * This suspend function uses the `Dispatchers.IO` context to perform database operations, ensuring
     * non-blocking execution. If the data is found, it will be deleted and a log entry will be created.
     * If the data is not found, a `DataNotFoundException` will be thrown.
     *
     * @param dataId the unique identifier of the data entity to delete.
     * @return `Unit`
     * @throws DataNotFoundException if no entity with the given ID exists in the repository.
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
