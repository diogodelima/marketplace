package pt.diogo.marketplace.dto

import jakarta.validation.constraints.NotBlank

data class RegisterRequestDto(

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank(message = "First name can not be null")
    val firstName: String,

    @field:NotBlank
    val lastName: String

)