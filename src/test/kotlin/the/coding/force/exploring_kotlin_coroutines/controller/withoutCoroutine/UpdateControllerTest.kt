package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest

class UpdateControllerTest : IntegrationTests() {
    @Test
    fun `Should update a task with success`() {
        // Arrange
        val createDataRequest = CreateDataRequest(
            status = DataStatusEnum.TODO
        )

        val entityBefore = repository.save(
            createDataRequest.toDto().toEntity()
        )

        println("entityBefore: $entityBefore")

        val entityUpdates = DataEntity(
            id = entityBefore.id,
            status = DataStatusEnum.COMPLETED.name,
        )

        // Action
        mockMvc.perform(
            put("/api/update/{id}", entityBefore.id)
                .content(objectMapper.writeValueAsString(entityUpdates))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        val entityAfter = repository.findAll().first()

        // Assert
        assertThat(entityAfter.id).isEqualTo(entityUpdates.id)
        assertThat(entityAfter.status).isEqualTo(entityUpdates.status)
    }

    @Test
    fun `should throw a DataNotFoundException when ID was not found to update`() {
        // Arrange
        val nonExistingId = 1000L
        val entity = DataEntity(id = nonExistingId, status = DataStatusEnum.COMPLETED.name)

        // Action
        val result = mockMvc.perform(
            put("/api/update/{id}", nonExistingId)
                .content(objectMapper.writeValueAsString(entity))
                .contentType(APPLICATION_JSON)
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
            "/api/update/$nonExistingId"
        )
        assertThat(objError.message).isEqualTo("Data with ID $nonExistingId was not found for update")
    }

    @Test
    fun `should throw a HttpMessageNotReadableException when requestBody was wrote incorrectly`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))

        val entityUpdated = "HelloWorld"

        // Action
        val result = mockMvc.perform(
            put("/api/update/{id}", entity.id)
                .content(objectMapper.writeValueAsString(entityUpdated))
                .contentType(APPLICATION_JSON)
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
            "org.springframework.http.converter.HttpMessageNotReadableException",
            "/api/update/${entity.id}"
        )
    }

    @Test
    fun `should throw a HttpMessageNotReadableException when some field on requestBody was wrote incorrectly`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))

        val entityUpdated = DataEntity(
            status = "HelloWorld"
        )

        // Action
        val result = mockMvc.perform(
            put("/api/update/{id}", entity.id)
                .content(objectMapper.writeValueAsString(entityUpdated))
                .contentType(APPLICATION_JSON)
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
            "org.springframework.http.converter.HttpMessageNotReadableException",
            "/api/update/${entity.id}"
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
