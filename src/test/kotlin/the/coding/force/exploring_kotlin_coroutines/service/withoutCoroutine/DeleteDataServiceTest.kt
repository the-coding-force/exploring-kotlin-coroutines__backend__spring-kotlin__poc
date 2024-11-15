package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import java.util.Optional
import kotlin.test.assertEquals

class DeleteDataServiceTest {
    private val dataRepository: DataRepository = mockk()
    private val deleteDataService = DeleteDataService(dataRepository);

    @Test
    fun `should delete data when ID exists`() {
        val existingId = 1L
        val mockData = mockk<DataEntity>()

        // simulate a mock data from DataEntity when ID is found
        every { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // simulate the execution from deleteById without make any action (just runs)
        every { dataRepository.deleteById(existingId) } just runs

        // calls the method delete
        deleteDataService.delete(existingId)

        // verify if the method findById was called one time
        verify(exactly = 1) { dataRepository.findById(existingId) }

        // verify if the method deleteById was called one time
        verify(exactly = 1) { dataRepository.deleteById(existingId) }
    }


    @Test
    fun `should throw exception when ID does not exist`() {
        val nonExistingId = 1000L

        // simulate a empty mock data when it does not find the data with this ID
        every { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // verify if the exception DataNotFoundException was thrown
        val exception = assertThrows<DataNotFoundException> {
            deleteDataService.delete(nonExistingId)
        }

        // verify if the message of exception is correct
        assertEquals(
            "Data with ID $nonExistingId was not found for deletion",
            exception.message
        )

        // verify if the method findById was called exactly one time
        verify(exactly = 1) { dataRepository.findById(nonExistingId) }

        // verify if the method deleteById was not called any time
        verify(exactly = 0) { dataRepository.deleteById(nonExistingId) }
    }

}