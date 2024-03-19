package com.exercise.loans.controller

import com.exercise.loans.dto.Response
import com.exercise.loans.constant.LoanConstant
import com.exercise.loans.dto.ErrorResponse
import com.exercise.loans.dto.Loan
import com.exercise.loans.dto.ProjectContactInfo
import com.exercise.loans.service.LoanService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "CRUD REST APIs for Loans in X Bank",
    description = "CRUD REST APIs in X Bank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class LoanController(
    private val loanService: LoanService,
    private val environment: Environment,
    private val projectContactInfo: ProjectContactInfo,
) {

    @Value("\${build.version}")
    val buildVersion: String? = null
    
    @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create new loan inside X Bank"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PostMapping("/create")
    fun create(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        loanService.createLoan(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                Response(
                    statusCode = LoanConstant.STATUS_201,
                    statusMessage = LoanConstant.MESSAGE_201
                )
            )
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/fetch")
    fun fetch(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Loan> {
        val loan = loanService.fetchLoanInfo(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loan)
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PutMapping("/update")
    fun update(@RequestBody @Valid loan: Loan): ResponseEntity<Response> {
        val isUpdated = loanService.updateLoan(loan)

        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_200,
                        statusMessage = LoanConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_417,
                        statusMessage = LoanConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @DeleteMapping("/delete")
    fun delete(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        val isDeleted = loanService.deleteLoan(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_200,
                        statusMessage = LoanConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = LoanConstant.STATUS_417,
                        statusMessage = LoanConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }

    @Operation(
        summary = "Get Build Information",
        description = "Get Build information that is deployed into cards microservice"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/build-info")
    fun getBuildInfo(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion)
    }

    @Operation(
        summary = "Get Java version",
        description = "Get Java version that is installed into cards microservice"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/java-version")
    fun getJavaVersion(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME") ?: "null, need permission")
    }

    @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/contact-info")
    fun getContactInfo(): ResponseEntity<ProjectContactInfo> {
        return ResponseEntity.status(HttpStatus.OK).body(projectContactInfo)
    }
}
