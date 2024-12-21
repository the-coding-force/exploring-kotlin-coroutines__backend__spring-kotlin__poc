package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.controller.handler.ResponseError
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class DeleteControllerCoroutineTest : IntegrationTests() {
    @Test
    fun `should delete a entity successfully`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))
        println("entity: $entity")

        // Action
        mockMvc.perform(
            delete("/api/coroutine/delete/{id}", entity.id)
                .accept(APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andReturn()
            .also {
                mockMvc.perform(asyncDispatch(it))
                    .andExpect(status().isOk)
            }

        val entityDeleted = repository.findById(entity.id!!)

        // Assert
        assertThat(entityDeleted).isEmpty
    }

    @Test
    fun `should throw a DataNotFoundException when ID was not found`() {
        // Arrange
        val nonExistingId = 1000L

        // Action
        val result = mockMvc.perform(
            delete("/api/coroutine/delete/{id}", nonExistingId)
                .accept(APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andReturn()
            .also {
                mockMvc.perform(asyncDispatch(it))
                    .andExpect(status().isNotFound)
            }

        val objError = getResponseErrorObj(result)
        // Assert
        assertObjError(
            objError,
            404,
            "NOT_FOUND",
            "the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException",
            "/api/coroutine/delete/$nonExistingId"
        )
    }

    @Test
    fun `should throw a MethodArgumentTypeMismatchException when ID was wrote incorrectly`() {
        // Arrange
        val incorrectData = "HelloWorld"

        // Action
        val result = mockMvc.perform(
            delete("/api/coroutine/delete/{id}", incorrectData)
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
            "/api/coroutine/delete/$incorrectData"
        )
    }

    private fun getResponseErrorObj(result: MvcResult) = objectMapper.readValue(
        result.response.contentAsString,
        ResponseError::class.java
    )

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
}
