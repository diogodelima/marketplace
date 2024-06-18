package pt.diogo.marketplace.service

import org.springframework.stereotype.Service
import pt.diogo.marketplace.model.CartItem
import pt.diogo.marketplace.repository.CartItemRepository

@Service
class CartItemServiceImpl(

    private val cartItemRepository: CartItemRepository

): CartItemService {

    override fun save(cartItem: CartItem): CartItem {
        return cartItemRepository.save(cartItem)
    }

    override fun delete(cartItemId: Long) {
        cartItemRepository.deleteById(cartItemId)
    }

    override fun getById(cartItemId: Long): CartItem? {
        return cartItemRepository.findById(cartItemId).orElse(null)
    }

}