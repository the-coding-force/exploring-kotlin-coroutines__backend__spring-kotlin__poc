package the.coding.force.exploring_kotlin_coroutines.service.coroutine

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import the.coding.force.exploring_kotlin_coroutines.dto.DataDto
import the.coding.force.exploring_kotlin_coroutines.dto.toEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

class CreateDataServiceCoroutineTest {
    private val dataRepository: DataRepository = mockk()
    private val createDataServiceCoroutine = CreateDataServiceCoroutine(dataRepository)

    @Test
    fun `should save data using coroutines`() = runTest {
        // create dto for createDataService param
        val dataDto = DataDto(DataStatusEnum.TODO)

        // every time dataRepository was called its return the first argument saved
        coEvery { dataRepository.save(any()) } answers { firstArg() }
        createDataServiceCoroutine.create(dataDto)

        // verify if method save from dataRepository was called exactly one time
        coVerify(exactly = 1) { dataRepository.save(dataDto.toEntity()) }
    }
}
