package com.exercise.loans.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information"
)
data class ErrorResponse(
    @Schema(
        description = "API path invoked by client"
    )
    val apiPath: String,

    @Schema(
        description = "Error code representing the error happened"
    )
    val errorCode: HttpStatus,

    @Schema(
        description = "Error message representing the error happened"
    )
    val errorMessage: String,

    @Schema(
        description = "Time representing when the error happened"
    )
    val errorTime: LocalDateTime, 
)
