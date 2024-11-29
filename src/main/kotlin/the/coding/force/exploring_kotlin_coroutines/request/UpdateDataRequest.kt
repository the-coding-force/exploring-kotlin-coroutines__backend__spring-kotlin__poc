package the.coding.force.exploring_kotlin_coroutines.request

import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

data class UpdateDataRequest(
    val status: DataStatusEnum
)
