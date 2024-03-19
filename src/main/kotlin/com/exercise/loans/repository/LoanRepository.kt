package com.exercise.loans.repository

import com.exercise.loans.entity.LoanEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface LoanRepository: JpaRepository<LoanEntity, Long> {
    fun findByMobileNumber(mobileNumber: String): Optional<LoanEntity>
}
