package the.coding.force.exploring_kotlin_coroutines.request

import io.swagger.v3.oas.annotations.media.Schema
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

@Schema(
    description = "A DTO (data transfer object) used to send on request " +
        "for save several entities how load test"
)
data class BulkCreateDataRequest(
    @Schema(
        description = "Quantity of entities to save in database",
        example = "500"
    )
    val quantity: Int,

    @Schema(
        description = "A Enum that represents the status of task"
    )
    val status: DataStatusEnum?
)
