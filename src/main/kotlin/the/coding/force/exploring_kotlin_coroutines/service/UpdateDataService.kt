package the.coding.force.exploring_kotlin_coroutines.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.Exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto

@Service
class UpdateDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    fun update(dataId: Long, body: CreateDataRequest) {
        val data = dataRepository.findById(dataId).orElse(null)

        data?.let {
            val dto = body.toDto()
            val entity = dto.toEntity()
            it.status = entity.status

            dataRepository.save(it)
            logger.info { "UpdateDataService.update, data with id ${it.id} was updated" }
        } ?: run {
            throw DataNotFoundException("the data with $dataId was not found to update it")
        }
    }
}
