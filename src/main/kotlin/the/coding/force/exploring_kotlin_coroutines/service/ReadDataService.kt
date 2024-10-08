package the.coding.force.exploring_kotlin_coroutines.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.Exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.response.ReadDataResponse

@Service
class ReadDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

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
