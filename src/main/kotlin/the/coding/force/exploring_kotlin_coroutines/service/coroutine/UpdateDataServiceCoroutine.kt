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
 * This Service has the suspend method `update` inside coroutine context to update a `DataEntity`
 *
 * @author Lucas Terra
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
     * received a `dataId` and a `createDataRequest` from suspend controller update
     * and try to find the data to update it with the new information, if the data exists
     * it will be updated with createDataRequest info, case the data does not exist an exception
     * `DataNotFoundException` will be thrown. The `logger` will register the status of update operation
     *
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from suspend controller `update` to find in database
     * @param createDataRequest
     * @return `Unit`
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     * @see createDataRequest
     * @see logger
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
