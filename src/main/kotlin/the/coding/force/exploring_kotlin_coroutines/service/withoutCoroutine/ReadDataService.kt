package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse

/**
 * @author Lucas Terra
 * @param dataRepository repository from DataEntity
 * @property read method to get a `ReadDataResponse`
 */
@Service
class ReadDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Lucas Terra
     * @param dataId ID from `DataEntity` received from controller `read` to find in the database
     * @return `ReadDataResponse` case a register exists in the database
     * @throws DataNotFoundException when the data with `DataId` was not found this exception is thrown
     * @see ReadDataResponse
     */
    fun read(dataId: Long): ReadDataResponse {
        return dataRepository.findById(dataId)
            .map { data ->
                logger.info { "ReadDataService.read: Data with ID ${data.id} was found" }
                ReadDataResponse(data.status)
            }
            .orElseThrow {
                DataNotFoundException("Data with ID $dataId was not found to retrieve it")
            }
    }
}
