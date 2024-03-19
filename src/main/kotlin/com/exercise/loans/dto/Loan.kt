package com.exercise.loans.dto

import com.exercise.loans.entity.LoanEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

@Schema(
    name = "Loans",
    description = "Schema to hold Loan information"
)
data class Loan(
    @field:NotEmpty(message = "Mobile Number can not be a null or empty")
    @field:Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
    @Schema(
        description = "Mobile Number of Customer", example = "4365327698"
    )
    var mobileNumber: String,

    @field:NotEmpty(message = "Loan Number can not be a null or empty")
    @field:Pattern(regexp = "(^$|[0-9]{10})", message = "LoanNumber must be 10 digits")
    @Schema(
        description = "Loan Number of the customer", example = "548732457654"
    )
    var loanNumber: String,

    @field:NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(
        description = "Type of the loan", example = "Home Loan"
    )
    var loanType: String,

    @field:Positive(message = "Total loan amount should be greater than zero")
    @Schema(
        description = "Total loan amount", example = "100000"
    )
    var totalLoan: Int,

    @field:PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
        description = "Total loan amount paid", example = "1000"
    )
    var amountPaid: Int,

    @field:PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
        description = "Total outstanding amount against a loan", example = "99000"
    )
    var outstandingAmount: Int,
) {
    constructor(loanEntity: LoanEntity) : this(
        mobileNumber = loanEntity.mobileNumber,
        loanNumber = loanEntity.loanNumber,
        loanType = loanEntity.loanType,
        totalLoan = loanEntity.totalLoan,
        amountPaid = loanEntity.amountPaid,
        outstandingAmount = loanEntity.outstandingAmount,
    )
}
