package the.coding.force.exploring_kotlin_coroutines.dto

import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

data class BulkCreateDataDto(
    val quantity: Int,
    val status: DataStatusEnum
)