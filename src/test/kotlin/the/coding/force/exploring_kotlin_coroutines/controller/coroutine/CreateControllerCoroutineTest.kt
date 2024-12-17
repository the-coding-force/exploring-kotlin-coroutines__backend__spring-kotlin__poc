package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import java.time.Duration

class CreateControllerCoroutineTest : IntegrationTests() {
    @Test
    fun `should create a entity successfully - controller with coroutine`() {
        // Arrange
        val entity = DataEntity(
            status = DataStatusEnum.TODO.name
        )

        // Action
        mockMvc.perform(
            post("/api/coroutine/create")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity))
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        Awaitility.await()
            .atMost(Duration.ofSeconds(10))
            .untilAsserted {
                val entityCreated = repository.findAll().first()

                // Assert
                assertThat(entityCreated.id).isNotNull()
                assertThat(entityCreated.status).isEqualTo(DataStatusEnum.TODO.name)
            }
    }
}
