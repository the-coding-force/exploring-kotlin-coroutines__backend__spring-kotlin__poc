package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class ReadControllerTest : IntegrationTests() {

    @Test
    fun `should get a entity successfully - controller without coroutine`() {
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
}
