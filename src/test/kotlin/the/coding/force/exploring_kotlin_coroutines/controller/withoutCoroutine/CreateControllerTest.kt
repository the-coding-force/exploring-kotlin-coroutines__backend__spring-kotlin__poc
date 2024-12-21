package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class CreateControllerTest : IntegrationTests() {
    @Test
    fun `should create a entity successfully`() {
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

    @Test
    fun `should throw a HttpMessageNotReadableException when requestBody was wrote incorrectly`() {
        // Arrange
        val incorrectData = "helloWorld"

        // Action
        val result = mockMvc.perform(
            post("/api/create")
                .content(incorrectData)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        val objError = getResponseErrorObj(result)
        // Assert
        assertObjError(objError)
    }

    @Test
    fun `should throw a HttpMessageNotReadableException when some field on requestBody was wrote incorrectly`() {
        // Arrange
        val incorrectData = DataEntity(status = "helloWorld")

        // Action
        val result = mockMvc.perform(
            post("/api/create")
                .content(objectMapper.writeValueAsString(incorrectData))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        val objError = getResponseErrorObj(result)
        // Assert
        assertObjError(objError)
    }

    private fun assertObjError(objError: ResponseError) {
        assertThat(objError.timestamp).isNotNull()
        assertThat(objError.status).isEqualTo(400)
        assertThat(objError.error).isEqualTo("BAD_REQUEST")
        assertThat(objError.message).isNotNull()
        assertThat(objError.exceptionClass).isEqualTo("org.springframework.http.converter.HttpMessageNotReadableException")
        assertThat(objError.path).isEqualTo("/api/create")
    }

    private fun getResponseErrorObj(result: MvcResult) = objectMapper.readValue(
        result.response.contentAsString,
        ResponseError::class.java
    )
}
