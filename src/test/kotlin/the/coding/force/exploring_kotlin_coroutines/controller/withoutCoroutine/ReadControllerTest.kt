package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class ReadControllerTest : IntegrationTests() {

    @Test
    fun `should get a entity successfully`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))
        println("entity: $entity")

        // Action
        mockMvc.perform(
            get("/api/read/{id}", entity.id)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        val entityCreated = repository.findById(entity.id!!)
        // Assert
        assertThat(entityCreated.get().id).isEqualTo(entity.id)
        assertThat(entityCreated.get().status).isEqualTo(entity.status)
    }

    @Test
    fun `should throw a DataNotFoundException when ID was not found to get`() {
        // Arrange
        val nonExistingId = 1000L

        // Action
        val result = mockMvc.perform(
            get("/api/read/{id}", nonExistingId)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andReturn()

        val objError = getResponseErrorObj(result)
        // Assert
        assertObjError(
            objError,
            404,
            "NOT_FOUND",
            "the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException",
            "/api/read/$nonExistingId"
        )
        assertThat(objError.message).isEqualTo("Data with ID $nonExistingId was not found to retrieve it")
    }

    @Test
    fun `should throw a MethodArgumentTypeMismatchException when ID was wrote incorrectly`() {
        // Arrange
        val incorrectData = "HelloWorld"

        // Action
        val result = mockMvc.perform(
            get("/api/read/{id}", incorrectData)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        val objError = getResponseErrorObj(result)
        // Assert
        assertObjError(
            objError,
            400,
            "BAD_REQUEST",
            "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException",
            "/api/read/$incorrectData"
        )
    }

    private fun assertObjError(
        objError: ResponseError,
        status: Int,
        error: String,
        exceptionClass: String,
        path: String
    ) {
        assertThat(objError.timestamp).isNotNull()
        assertThat(objError.status).isEqualTo(status)
        assertThat(objError.error).isEqualTo(error)
        assertThat(objError.message).isNotNull()
        assertThat(objError.exceptionClass).isEqualTo(exceptionClass)
        assertThat(objError.path).isEqualTo(path)
    }

    private fun getResponseErrorObj(result: MvcResult) = objectMapper.readValue(
        result.response.contentAsString,
        ResponseError::class.java
    )
}
