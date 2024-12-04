package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
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
class ReadDataServiceCoroutineTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var readDataServiceCoroutine: ReadDataServiceCoroutine

    @Test
    fun `should get data when ID exists`() = runTest {
        // Arrange: Scenario config
        val existingId = 1L
        val mockData = mockk<DataEntity> {
            every { id } returns existingId
            every { status } returns "VALID"
        }
        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // Action: Execution of service
        val response = readDataServiceCoroutine.read(existingId)

        // Assert: verify results
        assertEquals("VALID", response.status)
        coVerify(exactly = 1) { dataRepository.findById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        // Arrange: Scenario config
        val nonExistingId = 1000L
        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action: Execution of service
        val exception = assertThrows<DataNotFoundException> {
            readDataServiceCoroutine.read(nonExistingId)
        }

        // Assert: Verify results
        assertEquals(
            "Data with ID $nonExistingId was not found to retrieve it",
            exception.message
        )
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }
    }
}
