package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * @author Lucas Terra
 * @param dataRepository repository from `DataEntity`
 * @property delete method to delete a `DataEntity`
 */
@Service
class DeleteDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from controller `delete` to find in the database
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
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
