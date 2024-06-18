package pt.diogo.marketplace.dto

class CartResponseDto(

    val cartItems: Collection<CartItemResponseDto>,
    val total: Double,
    val subtotal: Double,
    val discount: Double

)