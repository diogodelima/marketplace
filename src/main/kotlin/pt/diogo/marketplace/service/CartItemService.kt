package pt.diogo.marketplace.service

import pt.diogo.marketplace.model.CartItem

interface CartItemService {

    fun save(cartItem: CartItem): CartItem

    fun delete(cartItemId: Long)

    fun getById(cartItemId: Long): CartItem?

}