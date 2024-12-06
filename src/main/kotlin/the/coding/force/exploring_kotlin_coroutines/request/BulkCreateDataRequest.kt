package the.coding.force.exploring_kotlin_coroutines.request

import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

data class BulkCreateDataRequest(
    val quantity: Int,
    val status: DataStatusEnum?
)
