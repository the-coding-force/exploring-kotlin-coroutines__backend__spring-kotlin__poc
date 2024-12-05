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
* This Service has the suspend method `update` inside coroutine context to update a `DataEntity`
*
* @author Adriano Santos
* @param dataRepository repository from DataEntity
* @property update suspend method to update a `DataEntity` in coroutine context
*/
@Service
class UpdateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    // logger to register the status of suspend method update
    private val logger = KotlinLogging.logger { }

    /**
     * received a `UpdateDataDto` from suspend controller `update`
     * and try to find the data to update it with the new information, if the data exists
     * it will be updated with UpdateDataDto status info, case the data does not exist an exception
     * `DataNotFoundException` will be thrown.
     * The `logger` will register the status of update operation
     *
     * @author Adriano Santos
     * @param dto is a data transfer objet from UpdateDataDto received from controller update
     * @return `Unit`
     * @throws DataNotFoundException when the data with `ID` inside UpdateDataDto was not found this exception is thrown
     * @see UpdateDataDto
     * @see logger
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
