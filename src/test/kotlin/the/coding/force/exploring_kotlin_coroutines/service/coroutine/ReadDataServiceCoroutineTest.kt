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
        val existingId = 1L

        // creating a mock DataEntity as response for findById
        val mockData = mockk<DataEntity> {
            every { id } returns existingId
            every { status } returns "VALID"
        }

        // every time that findById was called it will return an optional of mockData
        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)

        val response = readDataServiceCoroutine.read(existingId)

        // verify if the response status is correctly
        assertEquals("VALID", response.status)

        // verify if the method findById is called exactly one time
        coVerify(exactly = 1) { dataRepository.findById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        val nonExistingId = 1000L

        // every time that findById was called it will return an optional of empty
        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // verify if the exception DataNotFoundException was thrown
        val exception = assertThrows<DataNotFoundException> {
            readDataServiceCoroutine.read(nonExistingId)
        }

        // verify if the message of exception is correct
        assertEquals(
            "Data with ID $nonExistingId was not found to retrieve it",
            exception.message
        )

        // verify if the method findById is called exactly one time
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }
    }
}
