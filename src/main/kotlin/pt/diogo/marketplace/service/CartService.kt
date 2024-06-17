package pt.diogo.marketplace.service

import pt.diogo.marketplace.model.Cart

interface CartService {

    fun save(cart: Cart): Cart

}