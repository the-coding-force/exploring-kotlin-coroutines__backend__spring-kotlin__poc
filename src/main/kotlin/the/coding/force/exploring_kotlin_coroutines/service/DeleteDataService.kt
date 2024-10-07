package the.coding.force.exploring_kotlin_coroutines.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.Exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class DeleteDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    fun delete(dataId: Long) {
        val data = dataRepository.findById(dataId).orElse(null)

        data?.let {
            dataRepository.deleteById(dataId)
            logger.info { "DeleteDataService.delete, data with id ${it.id} was deleted" }
        } ?: run {
            throw DataNotFoundException("data with $dataId was not found to delete it")
        }
    }
}
