package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
/**
 * Service responsible for testing the speed of I/O operations involving multiple `DataEntity` instances.
 * It performs asynchronous creation of entities using coroutines to improve performance.
 *
 * @author José Iêdo
 * @param dataRepository Repository used to persist instances of `DataEntity`.
 */
@Service
class BulkCreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    // Logger to record the status of the execution of the `create` suspend function.
    private val logger = KotlinLogging.logger {}

    /**
     * Creates a list of `DataEntity` instances in the database asynchronously.
     *
     * For each value in `quantity`, a coroutine is launched to execute a task asynchronously.
     * Once all coroutines are created, the `awaitAll` function waits for their completion.
     * After all coroutines finish, a success message is logged.
     *
     * @param quantity Number of `DataEntity` instances to be saved in the database.
     * @param dto Data Transfer Object (DTO) received from the create controller.
     * @return `Unit` (no return value).
     */
    suspend fun create(quantity: Int, dto: DataDto) = coroutineScope {
        val coroutines = (1..quantity).map { index ->
            async {
                fakeNetworkCall()
                dataRepository.save(dto.toEntity())
                logger.info { "BulkCreateDataService.create: Entity $index saved successfully." }
            }
        }

        // Wait for all coroutines to complete.
        coroutines.awaitAll()

        logger.info { "BulkCreateDataService.create: All entities saved successfully." }
    }

    /**
     * Simulates an asynchronous external network call.
     * Used to test asynchronous operations within the `create` method.
     *
     * @throws InterruptedException if the delay is interrupted.
     */
    suspend fun fakeNetworkCall() {
        delay(1000)
    }
}
