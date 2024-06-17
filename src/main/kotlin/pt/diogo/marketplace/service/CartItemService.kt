package pt.diogo.marketplace.service

import pt.diogo.marketplace.model.CartItem

interface CartItemService {

    fun save(cartItem: CartItem): CartItem

    fun getCartItemByCartId(cartId: Long): Collection<CartItem>

}