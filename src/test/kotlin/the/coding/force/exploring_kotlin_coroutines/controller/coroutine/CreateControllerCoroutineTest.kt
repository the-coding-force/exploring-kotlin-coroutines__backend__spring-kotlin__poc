package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class CreateControllerCoroutineTest : IntegrationTests() {
    @Test
    fun `should create a entity successfully`() {
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
            .andExpect(request().asyncStarted())
            .andReturn()
            .also {
                mockMvc.perform(asyncDispatch(it))
                    .andExpect(status().isOk)
            }

        val entityCreated = repository.findAll().first()

        // Assert
        assertThat(entityCreated.id).isNotNull()
        assertThat(entityCreated.status).isEqualTo(DataStatusEnum.TODO.name)
    }

    @Test
    fun `should throw a HttpMessageNotReadableException when requestBody was wrote incorrectly`() {
        // Arrange
        val incorrectData = "HelloWorld"

        // Action
        mockMvc.perform(
            post("/api/coroutine/create")
                .content(incorrectData)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should throw a HttpMessageNotReadableException when some field on requestBody was wrote incorrectly`() {
        // Arrange
        val entity = DataEntity(status = "HelloWorld")

        // Action
        mockMvc.perform(
            post("/api/coroutine/create")
                .content(objectMapper.writeValueAsString(entity))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }
}
