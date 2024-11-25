package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
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
class UpdateDataServiceCoroutineTest {
   @MockK private lateinit var dataRepository: DataRepository
   @InjectMockKs private lateinit var updateDataServiceCoroutine: UpdateDataServiceCoroutine

    @Test
    fun `should update entity when ID exists`() = runTest {
        val existingId = 1L
        val existingBody = CreateDataRequest(DataStatusEnum.COMPLETED)

        // creating a mock from DataEntity
        val mockData = DataEntity(existingId, DataStatusEnum.TODO.name)

        // when the method findById was called always return an optional of mockData
        coEvery { dataRepository.findById(existingId) } returns Optional.of(mockData)

        // when the method save was called, it will return the same object updated
        coEvery { dataRepository.save(any()) } answers { firstArg() }

        updateDataServiceCoroutine.update(existingId, existingBody)

        // verify if the method findById is called exactly one time
        coVerify(exactly = 1) { dataRepository.findById(existingId) }

        // update the mockData with the actual info
        val updatedData = mockData.copy(status = existingBody.toDto().toEntity().status)

        // verify if the method save with the updated object is called exactly one time
        coVerify(exactly = 1) { dataRepository.save(updatedData) }
    }

    @Test
    fun `should throw exception when ID does not exist`() = runTest {
        val nonExistingId = 1000L
        val existingBody = CreateDataRequest(DataStatusEnum.COMPLETED)

        // when the method findById was called always return an optional of empty
        coEvery { dataRepository.findById(nonExistingId) } returns Optional.empty()

        // verify if the exception DataNotFoundException was thrown
        val exception = assertThrows<DataNotFoundException> {
            updateDataServiceCoroutine.update(nonExistingId, existingBody)
        }
        // verify if the message of exception is correct
        assertEquals(
            "Data with ID $nonExistingId was not found for update",
            exception.message
        )
        // verify if the method findById was called exactly one time
        coVerify(exactly = 1) { dataRepository.findById(nonExistingId) }

        // verify if the method save was not called any time
        coVerify(exactly = 0) { dataRepository.save(any()) }
    }
}
