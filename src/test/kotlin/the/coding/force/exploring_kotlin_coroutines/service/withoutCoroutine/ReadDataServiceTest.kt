package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
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
class ReadDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var readDataService: ReadDataService

    @Test
    fun `should get data when ID exists`() {
        // Arrange
        val existingId = 1L
        val mockData = mockk<DataEntity> {
            every { id } returns existingId
            every { status } returns "VALID"
        }
        every { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // Action
        val response = readDataService.read(existingId)

        // Assert
        assertEquals("VALID", response.status)
        verify(exactly = 1) { dataRepository.findById(existingId) }
    }

    @Test
    fun `should throw exception when ID does not exist`() {
        // Arrange
        val nonExistingId = 1000L
        every { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // Action
        val exception = assertThrows<DataNotFoundException> {
            readDataService.read(nonExistingId)
        }

        // Assert
        assertEquals(
            "Data with ID $nonExistingId was not found to retrieve it",
            exception.message
        )
        verify(exactly = 1) { dataRepository.findById(nonExistingId) }
    }
}
