package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.UpdateDataDto
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * @author Adriano Santos
 * @param dataRepository repository from DataEntity
 * @property update suspend method to update a `DataEntity` in coroutine context
 */
@Service
class UpdateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Adriano Santos
     * @param dto is a data transfer objet from UpdateDataDto received from controller update
     * @throws DataNotFoundException when the data with `ID` inside UpdateDataDto was not found this exception is thrown
     * @see UpdateDataDto
     */
    suspend fun update(dto: UpdateDataDto): DataEntity = withContext(Dispatchers.IO) {
        dataRepository.findById(dto.id)
            .map { data ->
                data.copy(status = dto.status.toString())
                    .also { dataRepository.save(it) }
                    .also { logger.info { "UpdateDataService.update: Data with ID ${it.id} was updated, status: ${it.status}" } }
            }
            .orElseThrow { DataNotFoundException("Data with ID ${dto.id} was not found for update") }
    }
}
