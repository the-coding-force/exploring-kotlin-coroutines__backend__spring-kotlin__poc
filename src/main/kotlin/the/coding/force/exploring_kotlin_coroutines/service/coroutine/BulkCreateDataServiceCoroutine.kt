package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.springframework.stereotype.Service
import the.coding.force.exploring_kotlin_coroutines.dto.BulkCreateDataDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

/**
 * @author José Iêdo
 * @param dataRepository Repository used to persist instances of `DataEntity`.
 */
@Service
class BulkCreateDataServiceCoroutine(
    private val dataRepository: DataRepository,
) {
    private val logger = KotlinLogging.logger { }

    /**
     * @param quantity Number of `DataEntity` instances to be saved in the database.
     * @param dto Data Transfer Object (DTO) received from the controller `create`.
     */
    suspend fun create(quantity: Int, dto: BulkCreateDataDto) = coroutineScope {
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

    /**
     * @author Jose Iêdo
     */
    suspend fun fakeNetworkCall() {
        delay(1000)
    }
}
