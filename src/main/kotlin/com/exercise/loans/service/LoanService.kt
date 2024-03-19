package com.exercise.loans.service

import com.exercise.loans.exception.ResourceNotFoundException
import com.exercise.loans.constant.LoanConstant
import com.exercise.loans.dto.Loan
import com.exercise.loans.entity.LoanEntity
import com.exercise.loans.exception.LoanAlreadyExistException
import com.exercise.loans.repository.LoanRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

interface LoanService {
    fun createLoan(mobileNumber: String)
    
    fun fetchLoanInfo(mobileNumber: String): Loan
    
    fun updateLoan(loan: Loan): Boolean
    
    fun deleteLoan(mobileNumber: String): Boolean
}

@Service
class LoanServiceImpl(
    private val loanRepository: LoanRepository
) : LoanService {
    override fun createLoan(mobileNumber: String) {
        checkIfLoanAlreadyExist(mobileNumber)
        loanRepository.save(generateLoan(mobileNumber))
    }
    
    private fun checkIfLoanAlreadyExist(mobileNumber: String) {
        val optionalLoan = loanRepository.findByMobileNumber(mobileNumber)
        if (optionalLoan.isPresent) {
            throw LoanAlreadyExistException("Loan already registered with given mobileNumber $mobileNumber")
        }
    }
    
    private fun generateLoan(mobileNumber: String): LoanEntity {
        val randomLoanNumber = 1000000000L + Random.nextInt(900000000)
        return LoanEntity(
            mobileNumber = mobileNumber,
            loanNumber = randomLoanNumber.toString(),
            loanType = LoanConstant.HOME_LOAN,
            totalLoan = LoanConstant.NEW_LOAN_LIMIT,
            amountPaid = 0,
            outstandingAmount = LoanConstant.NEW_LOAN_LIMIT,
        )
    }

    override fun fetchLoanInfo(mobileNumber: String): Loan {
        val loanEntity = loanRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        }
        
        return Loan(loanEntity)
    }

    override fun updateLoan(loan: Loan): Boolean {
        val updatedLoanEntity = loanRepository.findByMobileNumber(loan.mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", loan.mobileNumber)
        }.copy(
            mobileNumber = loan.mobileNumber,
            loanNumber = loan.loanNumber,
            loanType = loan.loanType,
            totalLoan = loan.totalLoan,
            amountPaid = loan.amountPaid,
            outstandingAmount = loan.outstandingAmount,
        )
        
        loanRepository.save(updatedLoanEntity)
        return true
    }

    override fun deleteLoan(mobileNumber: String): Boolean {
        val loanEntity = loanRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        }
        
        loanRepository.deleteById(loanEntity.loanId)
        return true
    }
}
