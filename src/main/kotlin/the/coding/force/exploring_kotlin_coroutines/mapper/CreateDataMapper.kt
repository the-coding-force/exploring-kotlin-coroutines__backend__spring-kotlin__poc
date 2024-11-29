package the.coding.force.exploring_kotlin_coroutines.mapper

import the.coding.force.exploring_kotlin_coroutines.dto.CreateDataDto
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.request.CreateDataRequest

fun CreateDataRequest.toDto() = CreateDataDto(
    status = status ?: DataStatusEnum.TODO
)

fun CreateDataDto.toEntity() = DataEntity(
    id = null,
    status = status.name
)
