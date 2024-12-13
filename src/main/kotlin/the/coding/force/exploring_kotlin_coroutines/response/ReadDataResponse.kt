package the.coding.force.exploring_kotlin_coroutines.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "A DTO (data transfer object) used how response for the read service"
)
data class ReadDataResponse(
    @Schema(
        description = "A String that represents the status of task returned in database for read operation"
    )
    val status: String
)
