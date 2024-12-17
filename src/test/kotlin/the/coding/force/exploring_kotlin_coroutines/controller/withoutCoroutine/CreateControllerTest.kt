package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class CreateControllerTest : IntegrationTests() {
    @Test
    fun `should create a entity successfully - controller without coroutine`() {
        // Arrange
        val entity = DataEntity(
            status = DataStatusEnum.TODO.name
        )

        // Action
        mockMvc.perform(
            post("/api/create")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity))
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        // Assert
        val entityCreated = repository.findAll().first()

        assertThat(entityCreated.id).isNotNull()
        assertThat(entityCreated.status).isEqualTo(DataStatusEnum.TODO.name)
    }
}
