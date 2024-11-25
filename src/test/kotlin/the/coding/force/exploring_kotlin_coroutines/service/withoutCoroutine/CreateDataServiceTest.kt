package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@ExtendWith(MockKExtension::class)
class CreateDataServiceTest {
    @MockK private lateinit var dataRepository: DataRepository

    @InjectMockKs private lateinit var createDataService: CreateDataService

    @Test
    fun `should save entity`() {
        // create dto for createDataService param
        val dto = DataDto(DataStatusEnum.TODO)

        // every time dataRepository was called its return the first argument saved
        every { dataRepository.save(any()) } answers { firstArg() }
        createDataService.create(dto)

        // verify if method save from dataRepository was called exactly one time
        verify(exactly = 1) { dataRepository.save(dto.toEntity()) }
    }
}
