package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.BulkCreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.concurrent.CompletableFuture

/**
 * @author José Iêdo
 * @param dataRepository Repository used to persist instances of `DataEntity`.
 */
@Service
class BulkCreateDataService(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @author Jose Iêdo
     * @param quantity Number of `DataEntity` instances to be saved in the database.
     * @param dto Data Transfer Object (DTO) received from the controller `create`.
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
     * @author Jose Iêdo
     * @throws InterruptedException if the delay is interrupted.
     * @throws IllegalArgumentException if the value of millis is negative
     */
    fun fakeNetworkCall() {
        Thread.sleep(1000)
    }
}
