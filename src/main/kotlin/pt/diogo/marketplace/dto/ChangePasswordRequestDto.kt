package pt.diogo.marketplace.dto

import jakarta.validation.constraints.NotEmpty

class ChangePasswordRequestDto(

    @field:NotEmpty
    val currentPassword: String,

    @field:NotEmpty
    val newPassword: String,

    @field:NotEmpty
    val confirmNewPassword: String

)