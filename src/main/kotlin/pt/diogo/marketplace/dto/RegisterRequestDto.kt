package pt.diogo.marketplace.dto

data class RegisterRequestDto(

    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String

)