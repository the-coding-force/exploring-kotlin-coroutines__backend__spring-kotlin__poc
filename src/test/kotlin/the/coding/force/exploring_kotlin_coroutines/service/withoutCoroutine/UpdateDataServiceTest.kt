package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest
import the.coding.force.exploring_kotlin_coroutines.request.toDto
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class UpdateDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var updateDataService: UpdateDataService

    @Test
    fun `should update entity when ID exists`() {
        val existingId = 1L
        val existingBody = CreateDataRequest(DataStatusEnum.COMPLETED)

        // creating a mock from DataEntity
        val mockData = DataEntity(existingId, DataStatusEnum.TODO.name)

        // when the method findById was called always return an optional of mockData
        every { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // when the method save was called, it will return the same object updated
        every { dataRepository.save(any()) } answers { firstArg() }

        updateDataService.update(existingId, existingBody)

        // verify if the method findById is called exactly one time
        verify(exactly = 1) { dataRepository.findById(existingId) }

        // update the mockData with the actual info
        val updatedData = mockData.copy(status = existingBody.toDto().toEntity().status)

        // verify if the method save with the updated object is called exactly one time
        verify(exactly = 1) { dataRepository.save(updatedData) }
    }

    @Test
    fun `should throw exception when ID does not exist`() {
        val nonExistingId = 1000L
        val existingBody = CreateDataRequest(DataStatusEnum.COMPLETED)

        // when the method findById was called always return an optional of empty
        every { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // verify if the exception DataNotFoundException was thrown
        val exception = assertThrows<DataNotFoundException> {
            updateDataService.update(nonExistingId, existingBody)
        }
        // verify if the message of exception is correct
        assertEquals(
            "Data with ID $nonExistingId was not found for update",
            exception.message
        )
        // verify if the method findById was called exactly one time
        verify(exactly = 1) { dataRepository.findById(nonExistingId) }

        // verify if the method save was not called any time
        verify(exactly = 0) { dataRepository.save(any()) }
    }
}
