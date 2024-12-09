package the.coding.force.exploring_kotlin_coroutines.controller

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

class UpdateControllerTest : IntegrationTests() {

    @Test
    fun `Should update a task with success - Controller without coroutine`() {
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

        mockMvc.perform(
            put("/api/update/{id}", entityBefore.id)
                .content(objectMapper.writeValueAsString(entityUpdates))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        val entityAfter = repository.findAll().first()

        assertThat(entityAfter.id).isEqualTo(entityUpdates.id)
        assertThat(entityAfter.status).isEqualTo(entityUpdates.status)
    }

    @Test
    fun `Should update a task with success - Controller with coroutine`() {
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

        mockMvc.perform(
            put("/api/coroutine/update/{id}", entityBefore.id)
                .content(objectMapper.writeValueAsString(entityUpdates))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        Thread.sleep(1000) // Necessário para o teste passar

        val entityAfter = repository.findByIdOrNull(entityUpdates.id!!)

        assertThat(entityAfter!!.id).isEqualTo(entityUpdates.id)
        assertThat(entityAfter.status).isEqualTo(entityUpdates.status)
    }
}
