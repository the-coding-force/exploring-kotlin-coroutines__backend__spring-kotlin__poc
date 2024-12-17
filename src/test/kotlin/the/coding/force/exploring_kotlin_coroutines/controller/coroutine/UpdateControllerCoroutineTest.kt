package the.coding.force.exploring_kotlin_coroutines.controller.coroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest

class UpdateControllerCoroutineTest : IntegrationTests() {
    @Test
    fun `Should update a task with success - Controller with coroutine`() {
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
            put("/api/coroutine/update/{id}", entityBefore.id)
                .content(objectMapper.writeValueAsString(entityUpdates))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        Thread.sleep(1000) // required to pass the test
        val entityAfter = repository.findByIdOrNull(entityUpdates.id!!)

        // Assert
        assertThat(entityAfter!!.id).isEqualTo(entityUpdates.id)
        assertThat(entityAfter.status).isEqualTo(entityUpdates.status)
    }
}
