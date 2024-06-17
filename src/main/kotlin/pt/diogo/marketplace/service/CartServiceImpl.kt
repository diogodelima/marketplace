package pt.diogo.marketplace.service

import org.springframework.stereotype.Service
import pt.diogo.marketplace.model.Cart
import pt.diogo.marketplace.repository.CartRepository

@Service
class CartServiceImpl(

    private val cartRepository: CartRepository

): CartService {

    override fun save(cart: Cart): Cart {
        return cartRepository.save(cart)
    }

}