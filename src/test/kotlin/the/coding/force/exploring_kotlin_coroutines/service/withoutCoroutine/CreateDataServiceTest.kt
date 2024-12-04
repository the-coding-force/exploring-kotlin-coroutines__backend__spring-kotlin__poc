package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.mapper.toEntity
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@ExtendWith(MockKExtension::class)
class CreateDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository

    @InjectMockKs private lateinit var createDataService: CreateDataService

    @Test
    fun `should save entity`() {
        // Arrange: Scenario config
        val dto = CreateDataDto(DataStatusEnum.TODO)
        every { dataRepository.save(any()) } answers { firstArg() }

        // Action: Execution of service
        createDataService.create(dto)

        // Assert: Verify results
        verify(exactly = 1) { dataRepository.save(dto.toEntity()) }
    }
}
