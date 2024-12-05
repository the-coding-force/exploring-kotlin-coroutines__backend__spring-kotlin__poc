package the.coding.force.exploring_kotlin_coroutines.mapper

import the.coding.force.exploring_kotlin_coroutines.dto.BulkCreateDataDto
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum
import the.coding.force.exploring_kotlin_coroutines.request.BulkCreateDataRequest

fun BulkCreateDataRequest.toDto() = BulkCreateDataDto(
    quantity = this.quantity,
    status = this.status ?: DataStatusEnum.TODO
)

fun BulkCreateDataDto.toEntity() = DataEntity(
    id = null,
    status = this.status.name
)
