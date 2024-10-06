package the.coding.force.exploring_kotlin_coroutines.dto

import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

data class DataDto(
    val status: DataStatusEnum
)


fun DataDto.toEntity() = DataEntity(
    id = null,
    status = status.name
)
