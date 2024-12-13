package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * @author Adriano Santos
 * @param dataRepository repository from `DataEntity`
 * @property create method to create a `DataEntity`
 */
@Service
class CreateDataService(
    private val dataRepository: DataRepository
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Adriano Santos
     * @param dto data transfer object received from controller `create`
     */
    fun create(dto: CreateDataDto) = dataRepository
        .save(dto.toEntity())
        .also { logger.info { "CreateDataService.create, entity saved:$this" } }
}
