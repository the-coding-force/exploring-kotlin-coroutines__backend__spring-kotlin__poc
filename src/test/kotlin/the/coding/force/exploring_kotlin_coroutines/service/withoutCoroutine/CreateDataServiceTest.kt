package the.coding.force.exploring_kotlin_coroutines.service.withoutCoroutine

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import io.mockk.verify
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

class CreateDataServiceTest {
    private val dataRepository: DataRepository = mockk()
    private val createDataService = CreateDataService(dataRepository)

    @Test
    fun `should save entity`() {
        //create dto for createDataService parem
        val dto = DataDto(DataStatusEnum.TODO);

        every { dataRepository.save(any()) } answers { firstArg() }
        createDataService.create(dto);


        //verify if method save from dataRepository was called exactly one time
        verify(exactly = 1) { dataRepository.save(dto.toEntity()) }
    }
}