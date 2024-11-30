package the.coding.force.exploring_kotlin_coroutines.mapper

import the.coding.force.exploring_kotlin_coroutines.dto.UpdateDataDto
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.request.UpdateDataRequest

fun UpdateDataRequest.toDto(id: Long) = UpdateDataDto(
    id = id,
    status = DataStatusEnum.valueOf(status.name)
)
