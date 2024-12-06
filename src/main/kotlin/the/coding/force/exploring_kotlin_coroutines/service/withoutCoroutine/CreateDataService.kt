package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * This Service has the method `create` to create a `DataEntity`
 *
 * @author Adriano Santos
 * @param dataRepository repository from `DataEntity`
 * @property create method to create a `DataEntity`
 */
@Service
class CreateDataService(
    private val dataRepository: DataRepository
) {
    // logger to register the status of method `create`
    private val logger = KotlinLogging.logger { }

    /**
     * received a `dataDto` from controller create and convert it in a `DataEntity`to save in the database,
     * after this the logger will register the status of create operation
     *
     * @author Adriano Santos
     * @param dto data transfer object received from controller `create`
     * @return `Unit`
     * @see logger
     */
    fun create(dto: CreateDataDto) = dataRepository
        .save(dto.toEntity())
        .also { logger.info { "CreateDataService.create, entity saved:$this" } }
}
