package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * This Service has the suspend method `create` inside coroutine context to create a `DataEntity`
 *
 * @author Lucas Terra
 * @param dataRepository repository from `DataEntity`
 * @property create suspend method to create a `DataEntity` in coroutine context
 */
@Service
class CreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    // logger to register the status of suspend method create
    private val logger = KotlinLogging.logger { }

    /**
     * received a `dataDto` from suspend controller create and convert it in a `DataEntity`
     * to save in database inside a coroutine context, after this the logger will register the status
     * of create operation
     *
     * @author Lucas Terra
     * @param dataDto data transfer object received from suspend controller `create`
     * @return `Unit`
     * @see logger
     */
    suspend fun create(dataDto: DataDto) {
        withContext(Dispatchers.IO) {
            dataRepository.save(dataDto.toEntity())
            logger.info { "CreateDataService.create with coroutine, entity saved" }
        }
    }
}
