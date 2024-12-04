package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@ExtendWith(MockKExtension::class)
class CreateDataServiceCoroutineTest {
    @MockK private lateinit var dataRepository: DataRepository
    @InjectMockKs private lateinit var createDataServiceCoroutine: CreateDataServiceCoroutine

    @Test
    fun `should save data using coroutines`() = runTest {
        // Arrange: Scenario config
        val dataDto = CreateDataDto(DataStatusEnum.TODO)
        coEvery { dataRepository.save(any()) } answers { firstArg() }

        // Action: Execution of service
        createDataServiceCoroutine.create(dataDto)

        // Assert: verify results
        coVerify(exactly = 1) { dataRepository.save(dataDto.toEntity()) }
    }
}
