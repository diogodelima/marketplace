package pt.diogo.marketplace.dto

import jakarta.validation.constraints.NotNull

class AddProductCartRequestDto(

    @field:NotNull
    val productId: Long,

    @field:NotNull
    val amount: Int

)