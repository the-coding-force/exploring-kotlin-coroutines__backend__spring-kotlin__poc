package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.mapper.toDto
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.UpdateDataRequest
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class UpdateDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var updateDataService: UpdateDataService

    @Test
    fun `should update entity when ID exists`() {
        // Arrange: scenario config
        val existingId = 1L
        val existingBody = UpdateDataRequest(DataStatusEnum.COMPLETED)
        val mockData = DataEntity(existingId, DataStatusEnum.TODO.name)

        every { dataRepository.findById(existingId) } returns Optional.of(mockData)
        every { dataRepository.save(any()) } answers { firstArg() }

        // Act: Execution of action
        updateDataService.update(existingBody.toDto(existingId))

        // Assert: verify of results
        verify(exactly = 1) { dataRepository.findById(existingId) }
        val updatedData = mockData.copy(status = existingBody.status.name)
        verify(exactly = 1) { dataRepository.save(updatedData) }
    }

    @Test
    fun `should throw exception when ID does not exist`() {
        // Arrange: Scenario config
        val nonExistingId = 1000L
        val existingBody = UpdateDataRequest(DataStatusEnum.COMPLETED)

        every { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action: Execution of action
        val exception = assertThrows<DataNotFoundException> {
            updateDataService.update(existingBody.toDto(nonExistingId))
        }

        // Assert: Verify results
        assertEquals(
            "Data with ID $nonExistingId was not found for update",
            exception.message
        )
        verify(exactly = 1) { dataRepository.findById(nonExistingId) }
        verify(exactly = 0) { dataRepository.save(any()) }
    }
}
