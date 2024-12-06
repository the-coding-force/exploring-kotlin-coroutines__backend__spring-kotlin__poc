package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * This Service has the method `delete` to delete a `DataEntity`
 *
 * @author Lucas Terra
 * @param dataRepository repository from `DataEntity`
 * @property delete method to delete a `DataEntity`
 */
@Service
class DeleteDataService(
    private val dataRepository: DataRepository
) {
    // logger to register the status of method `delete`
    private val logger = KotlinLogging.logger { }

    /**
     * Received a `dataId` from controller `delete` and try to find the data to delete it,
     * if the data exists it will be deleted in the database, case the data does not exist an exception
     * `DataNotFoundException` will be thrown.
     * The logger will register the status of delete operation
     *
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from controller `delete` to find in the database
     * @return `Unit`
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     * @see logger
     */
    fun delete(dataId: Long) {
        dataRepository.findById(dataId)
            .ifPresentOrElse(
                { data ->
                    dataRepository.deleteById(dataId)
                    logger.info { "DeleteDataService.delete: Data with ID ${data.id} was deleted" }
                },
                {
                    throw DataNotFoundException("Data with ID $dataId was not found for deletion")
                }
            )
    }
}
