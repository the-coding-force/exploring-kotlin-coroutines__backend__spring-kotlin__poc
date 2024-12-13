package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse

/**
 * @author Lucas Terra
 * @param dataRepository repository from DataEntity
 * @property read suspend method to get a `ReadDataResponse` in coroutine context
 */
@Service
class ReadDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from suspend controller `read` to find in the database
     * @return `ReadDataResponse` case a register exists in the database
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     * @see ReadDataResponse
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
