package com.exercise.loans

import com.exercise.loans.dto.ProjectContactInfo
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@OpenAPIDefinition(
	info = Info(
		title = "Loan microservice REST API Documentation",
		description = "Loan Microservice is a restful HTTP Api service to handle bank Loans business logic such as create new loan, fetch loan info, update loan amount, and delete loan.",
		version = "v1",
		contact = Contact(
			name = "Rifqi Naufal Tohari",
			email = "rifqinaufaltohari@gmail.com",
			url = "https://www.linkedin.com/in/rifqi-naufal-tohari/"
		)
	),
	externalDocs = ExternalDocumentation(
		description =  "Source Code",
		url = "https://github.com/toharifqi/loans"
	)
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = [ProjectContactInfo::class])
class LoansApplication

fun main(args: Array<String>) {
	runApplication<LoansApplication>(*args)
}
