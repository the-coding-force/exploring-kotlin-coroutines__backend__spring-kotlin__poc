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

@Service
class BulkCreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun create(quantity: Int, dto: DataDto) = coroutineScope {
        val coroutines = (1..quantity).map { index ->
            async {
                fakeNetworkCall()
                dataRepository.save(dto.toEntity())
                logger.info { "BulkCreateDataService.create with coroutine, entity $index saved" }
            }
        }

        coroutines.awaitAll()

        logger.info { "BulkCreateDataService.create with coroutine, all entities saved" }
    }

    suspend fun fakeNetworkCall() {
        delay(1000)
    }
}