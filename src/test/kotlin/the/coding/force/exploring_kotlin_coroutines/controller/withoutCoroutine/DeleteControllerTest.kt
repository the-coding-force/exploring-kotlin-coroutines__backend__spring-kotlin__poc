package the.coding.force.exploring_kotlin_coroutines.controller.withoutCoroutine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import the.coding.force.exploring_kotlin_coroutines.IntegrationTests
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

class DeleteControllerTest : IntegrationTests() {
    @Test
    fun `should delete a entity successfully - controller without coroutine`() {
        // Arrange
        val entity = repository.save(DataEntity(status = DataStatusEnum.TODO.name))
        println("entity: $entity")

        // Action
        mockMvc.perform(
            delete("/api/delete/{id}", entity.id)
                .accept(APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        val entityDeleted = repository.findById(entity.id!!)

        // Assert
        assertThat(entityDeleted).isEmpty
    }
}
