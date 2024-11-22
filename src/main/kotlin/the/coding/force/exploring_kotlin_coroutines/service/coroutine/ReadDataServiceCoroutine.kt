package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse

/**
 * Service responsible for reading data entities using coroutines.
 *
 * This service provides a coroutine-based method to retrieve data entities from the repository.
 * A logger is used to track the status of the operation.
 *
 * @author Lucas Terra
 * @property dataRepository the repository used to retrieve data entities.
 */
@Service
class ReadDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * Retrieves a data entity by its ID using a coroutine context.
     *
     * This method queries the repository within an I/O dispatcher context and converts
     * the retrieved entity to a response object. If the entity is not found, an exception is thrown.
     *
     * @param dataId the unique identifier of the data entity to retrieve.
     * @return a `ReadDataResponse` containing the status of the retrieved data entity.
     * @throws DataNotFoundException if no data entity with the specified ID is found.
     */
    suspend fun read(dataId: Long): ReadDataResponse {
        return withContext(Dispatchers.IO) {
            dataRepository.findById(dataId)
                .map { data ->
                    logger.info { "ReadDataService.read with coroutine: Data with ID ${data.id} was found" }
                    ReadDataResponse(data.status)
                }
                .orElseThrow {
                    DataNotFoundException("Data with ID $dataId was not found to retrieve it")
                }
        }
    }
}
