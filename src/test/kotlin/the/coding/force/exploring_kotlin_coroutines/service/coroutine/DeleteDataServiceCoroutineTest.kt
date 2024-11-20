package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.Optional
import kotlin.test.assertEquals

class DeleteDataServiceCoroutineTest {
    private val dataRepository: DataRepository = mockk()
    private val deleteDataServiceCoroutine = DeleteDataServiceCoroutine(dataRepository)

    @Test
    fun `should delete data when ID exists`() = runTest {
        val existingId = 1L
        val mockData = mockk<DataEntity>()

        // simulate a mock data from DataEntity when ID is found
        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // simulate the execution from deleteById without make any action (just runs)
        coEvery { dataRepository.deleteById(existingId) } just runs

        // calls the method delete
        deleteDataServiceCoroutine.delete(existingId)

        // verify if the method findById was called one time
        coVerify(exactly = 1) { dataRepository.findById(existingId) }

        // verify if the method deleteById was called one time
        coVerify(exactly = 1) { dataRepository.deleteById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        val nonExistingId = 1000L

        // simulate an empty mock data when it does not find the data with this ID
        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // verify if the exception DataNotFoundException was thrown
        val exception = assertThrows<DataNotFoundException> {
            deleteDataServiceCoroutine.delete(nonExistingId)
        }

        // verify if the message of exception is correct
        assertEquals(
            "Data with ID $nonExistingId was not found for deletion",
            exception.message
        )

        // verify if the method findById was called exactly one time
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }

        // verify if the method deleteById was not called any time
        coVerify(exactly = 0) { dataRepository.deleteById(nonExistingId) }
    }
}
