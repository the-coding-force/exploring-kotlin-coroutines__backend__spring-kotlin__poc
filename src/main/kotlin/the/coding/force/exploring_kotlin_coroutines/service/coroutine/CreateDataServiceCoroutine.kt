package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class CreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun create(createDataDto: CreateDataDto) {
        withContext(Dispatchers.IO) {
            dataRepository.save(createDataDto.toEntity())
            logger.info { "CreateDataService.create with coroutine, entity saved" }
        }
    }
}
