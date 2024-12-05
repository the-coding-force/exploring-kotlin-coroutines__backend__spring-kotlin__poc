package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class DeleteDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var deleteDataService: DeleteDataService

    @Test
    fun `should delete data when ID exists`() {
        // Arrange
        val existingId = 1L
        val mockData = mockk<DataEntity>()

        every { dataRepository.findById(existingId) } returns Optional.of(mockData)
        every { dataRepository.deleteById(existingId) } just runs

        // Action
        deleteDataService.delete(existingId)

        // Assert
        verify(exactly = 1) { dataRepository.findById(existingId) }
        verify(exactly = 1) { dataRepository.deleteById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() {
        // Arrange
        val nonExistingId = 1000L
        every { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action
        val exception = assertThrows<DataNotFoundException> {
            deleteDataService.delete(nonExistingId)
        }

        // Assert
        assertEquals(
            "Data with ID $nonExistingId was not found for deletion",
            exception.message
        )
        verify(exactly = 1) { dataRepository.findById(nonExistingId) }
        verify(exactly = 0) { dataRepository.deleteById(nonExistingId) }
    }
}
