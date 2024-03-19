package com.exercise.loans.entity

import com.exercise.loans.dto.Loan
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "loan")
@EntityListeners(AuditingEntityListener::class)
data class LoanEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    val loanId: Long = 0L,

    val mobileNumber: String,

    val loanNumber: String,

    val loanType: String,

    val totalLoan: Int,

    val amountPaid: Int,

    val outstandingAmount: Int,

    @CreatedDate
    override var createdAt: LocalDateTime? = null,

    @CreatedBy
    override var createdBy: String? = null,

    @LastModifiedDate
    override var updatedAt: LocalDateTime? = null,

    @LastModifiedBy
    override var updatedBy: String? = null,
) : BaseEntity {
    constructor(loan: Loan) : this(
        mobileNumber = loan.mobileNumber,
        loanNumber = loan.loanNumber,
        loanType = loan.loanType,
        totalLoan = loan.totalLoan,
        amountPaid = loan.amountPaid,
        outstandingAmount = loan.outstandingAmount,
    )
}
