package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
/**
 * Service responsible for updating data entities using coroutines.
 *
 * This service provides a coroutine-based method to update data entities in the repository.
 * A logger is used to track the status of the operation.
 *
 * @author Lucas Terra
 * @property dataRepository the repository used to manage data entities.
 */
@Service
class UpdateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * Updates a data entity with new information using a coroutine context.
     *
     * This method retrieves the data entity by its ID, updates its properties based on the provided
     * request, and saves the changes back to the repository. Logs the status of the operation.
     * Throws an exception if the entity is not found.
     *
     * @param dataId the unique identifier of the data entity to update.
     * @param createDataRequest the request containing the updated data information.
     * @return `Unit`
     * @throws DataNotFoundException if no data entity with the specified ID is found.
     */
    suspend fun update(dataId: Long, createDataRequest: CreateDataRequest) {
        withContext(Dispatchers.IO) {
            dataRepository.findById(dataId).ifPresentOrElse(
                { data ->
                    val updatedData = data.copy(status = createDataRequest.toDto().toEntity().status)
                    dataRepository.save(updatedData)
                    logger.info { "UpdateDataService.update with coroutine: Data with ID ${updatedData.id} was updated" }
                },
                {
                    throw DataNotFoundException("Data with ID $dataId was not found for update")
                }
            )
        }
    }
}
