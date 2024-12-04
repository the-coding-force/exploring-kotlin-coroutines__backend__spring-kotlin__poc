package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
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
class UpdateDataServiceCoroutineTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var updateDataServiceCoroutine: UpdateDataServiceCoroutine

    @Test
    fun `should update entity when ID exists`() = runTest {
        // Arrange: Scenario config
        val existingId = 1L
        val existingBody = UpdateDataRequest(DataStatusEnum.COMPLETED)
        val mockData = DataEntity(existingId, DataStatusEnum.TODO.name)

        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)
        coEvery { dataRepository.save(any()) } answers { firstArg() }

        // Action: Execution of service
        updateDataServiceCoroutine.update(existingBody.toDto(existingId))

        // Assert: verify results
        coVerify(exactly = 1) { dataRepository.findById(existingId) }
        val updatedData = mockData.copy(status = existingBody.status.name)
        coVerify(exactly = 1) { dataRepository.save(updatedData) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        // Arrange: Scenario config
        val nonExistingId = 1000L
        val existingBody = UpdateDataRequest(DataStatusEnum.COMPLETED)

        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action: Execution of service
        val exception = assertThrows<DataNotFoundException> {
            updateDataServiceCoroutine.update(existingBody.toDto(nonExistingId))
        }

        // Assert: verify results
        assertEquals(
            "Data with ID $nonExistingId was not found for update",
            exception.message
        )
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }
        coVerify(exactly = 0) { dataRepository.save(any()) }
    }
}
