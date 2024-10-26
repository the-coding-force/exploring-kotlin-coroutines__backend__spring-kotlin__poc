package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.concurrent.CompletableFuture

@Service
class BulkCreateDataService(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    fun create(quantity: Int, dto: DataDto) {
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

    fun fakeNetworkCall() {
        Thread.sleep(1000)
    }
}