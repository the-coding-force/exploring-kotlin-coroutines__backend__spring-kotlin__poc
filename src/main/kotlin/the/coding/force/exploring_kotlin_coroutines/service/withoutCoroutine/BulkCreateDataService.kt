package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.BulkCreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.concurrent.CompletableFuture

/**
 * Service responsible for testing the speed of I/O operations involving multiple `DataEntity` instances.
 * It performs asynchronous creation of entities using `CompletableFuture` java class to improve performance.
 *
 * @author José Iêdo
 * @param dataRepository Repository used to persist instances of `DataEntity`.
 */
@Service
class BulkCreateDataService(
    private val dataRepository: DataRepository,
) {
    // Logger to record the status of the execution of the `create` function.
    private val logger = KotlinLogging.logger { }

    /**
     * Creates a list of `DataEntity` instances in the database asynchronously.
     * For each value in `quantity`, a `supplyAsync` block is launched to execute a task asynchronously.
     * Once all `supplyAsync` blocks are created and stored in a variable jobs
     * the `allOf` function  from `CompletableFuture` waits for their completion.
     * After all tasks finish, a success message is logged.
     *
     * @author Jose Iêdo
     * @param quantity Number of `DataEntity` instances to be saved in the database.
     * @param dto Data Transfer Object (DTO) received from the controller `create`.
     * @return `Unit` (no return value).
     */
    fun create(quantity: Int, dto: BulkCreateDataDto) {
        val jobs = (1..quantity).map { index ->
            CompletableFuture.supplyAsync {
                fakeNetworkCall()
                dataRepository.save(dto.toEntity())
                logger.info { "BulkCreateDataService.create without coroutines, entity $index saved" }
            }
        }

        CompletableFuture.allOf(*jobs.toTypedArray()).join()

        logger.info { "BulkCreateDataService.create without coroutines, all entities saved" }
    }

    /**
     * Simulates an asynchronous external network call.
     * Used to test asynchronous operations within the `create` method.
     *
     * @author Jose Iêdo
     * @throws InterruptedException if the delay is interrupted.
     * @throws IllegalArgumentException if the value of millis is negative
     */
    fun fakeNetworkCall() {
        Thread.sleep(1000)
    }
}
