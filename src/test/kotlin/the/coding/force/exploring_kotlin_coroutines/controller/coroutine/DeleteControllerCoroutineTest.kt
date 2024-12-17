package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import java.time.Duration

class DeleteControllerCoroutineTest : IntegrationTests() {
    @Test
    fun `should delete a entity successfully - controller with coroutine`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))
        println("entity: $entity")

        // Action
        mockMvc.perform(
            delete("/api/coroutine/delete/{id}", entity.id)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        Awaitility.await()
            .atMost(Duration.ofSeconds(10))
            .untilAsserted {
                val entityDeleted = repository.findById(entity.id!!)

                // Assert
                assertThat(entityDeleted).isEmpty
            }
    }
}
