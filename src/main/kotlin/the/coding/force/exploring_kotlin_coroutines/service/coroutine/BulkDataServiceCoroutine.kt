package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@Service
class BulkDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun create(quantity: Int) = coroutineScope {
        val coroutines = (1..quantity).map { index ->
            async {
                networkCall()
                dataRepository.save(DataEntity(id = null, status = "TODO"))
                logger.info { "BulkCreateDataService.create with coroutine, entity $index saved" }
            }
        }

        coroutines.awaitAll()

        logger.info { "BulkCreateDataService.create with coroutine, all entities saved" }
    }

    suspend fun networkCall() {
    }
}