package com.exercise.loans.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "loans")
data class ProjectContactInfo(
    val message: String,
    val contactDetails: Map<String, String>,
    val onCallSupport: List<String>
)
