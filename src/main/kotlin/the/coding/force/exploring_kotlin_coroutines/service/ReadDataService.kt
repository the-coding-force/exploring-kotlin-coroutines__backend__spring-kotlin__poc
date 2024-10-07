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
    private val logger = KotlinLogging.logger {  }

    fun read(dataId: Long): ReadDataResponse {
        val data = dataRepository.findById(dataId).orElse(null)

        data?.let {
            logger.info { "ReadDataService.read, data with id ${it.id} was found" }
            return ReadDataResponse(it.status);
        } ?: run {
            throw DataNotFoundException("Data with id $dataId was not found to get it")
        }
    }
}