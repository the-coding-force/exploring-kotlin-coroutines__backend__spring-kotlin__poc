package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * Service responsible for creating data entities using coroutines.
 *
 * This service provides a coroutine-based method to save data entities to the repository.
 * A logger is used to track the status of the operation.
 *
 * @author Lucas Terra
 * @property dataRepository the repository used to persist data entities.
 */
@Service
class CreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * Creates a new data entity using a coroutine context.
     *
     * This method saves the provided data to the repository within an I/O dispatcher context
     * and logs the result of the operation.
     *
     * @author lucas terra
     * @param dataDto the data transfer object containing the information to be saved.
     * @return `Unit`
     */
    suspend fun create(dataDto: DataDto) {
        withContext(Dispatchers.IO) {
            dataRepository.save(dataDto.toEntity())
            logger.info { "CreateDataService.create with coroutine, entity saved" }
        }
    }
}
