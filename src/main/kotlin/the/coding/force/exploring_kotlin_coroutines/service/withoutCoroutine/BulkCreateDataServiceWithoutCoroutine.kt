package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.concurrent.CompletableFuture

@Service
class BulkCreateDataServiceWithoutCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    fun create(quantity: Int) {
        val jobs = (1..quantity).map { index ->
            CompletableFuture.supplyAsync {
                networkCall()
                dataRepository.save(DataEntity(id = null, status = "TODO"))
                logger.info { "BulkCreateDataService.create without coroutines, entity $index saved" }
            }
        }

        CompletableFuture.allOf(*jobs.toTypedArray()).join()

        logger.info { "BulkCreateDataService.create without coroutines, all entities saved" }
    }

    fun networkCall() {
    }
}