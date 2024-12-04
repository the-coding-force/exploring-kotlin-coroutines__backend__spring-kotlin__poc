package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class DeleteDataServiceCoroutineTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var deleteDataServiceCoroutine: DeleteDataServiceCoroutine

    @Test
    fun `should delete data when ID exists`() = runTest {
        // Arrange: Scenario config
        val existingId = 1L
        val mockData = mockk<DataEntity>()

        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)
        coEvery { dataRepository.deleteById(existingId) } just runs

        // Action: Execution of service
        deleteDataServiceCoroutine.delete(existingId)

        // Assert: verify results
        coVerify(exactly = 1) { dataRepository.findById(existingId) }
        coVerify(exactly = 1) { dataRepository.deleteById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        // Arrange: Scenario config
        val nonExistingId = 1000L
        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action: Execution of service
        val exception = assertThrows<DataNotFoundException> {
            deleteDataServiceCoroutine.delete(nonExistingId)
        }

        // Assert: verify results
        assertEquals(
            "Data with ID $nonExistingId was not found for deletion",
            exception.message
        )
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }
        coVerify(exactly = 0) { dataRepository.deleteById(nonExistingId) }
    }
}
