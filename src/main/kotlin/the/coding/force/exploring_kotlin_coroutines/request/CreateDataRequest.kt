package the.coding.force.exploring_kotlin_coroutines.request

import io.swagger.v3.oas.annotations.media.Schema
import the.coding.force.exploring_kotlin_coroutines.enums.DataStatusEnum

@Schema(
    description = "A DTO (data transfer object) used to send on request for save a entity"
)
data class CreateDataRequest(
    @Schema(
        description = "A Enum that represents the status of task"
    )
    val status: DataStatusEnum?
)
