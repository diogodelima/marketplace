package pt.diogo.marketplace.dto

import jakarta.validation.constraints.NotNull

class RemoveProductCartRequestDto(

    @field:NotNull
    val productId: Long,

    @field:NotNull
    val amount: Int

)