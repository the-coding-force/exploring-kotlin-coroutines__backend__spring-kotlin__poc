package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class CreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val logger = KotlinLogging.logger { }

    suspend fun create(dataDto: DataDto) {
        withContext(ioDispatcher) {
            dataRepository.save(dataDto.toEntity())
            logger.info { "CreateDataService.create, entity saved" }
        }
    }
}
