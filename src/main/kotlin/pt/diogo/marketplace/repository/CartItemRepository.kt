package pt.diogo.marketplace.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.diogo.marketplace.model.CartItem

@Repository
interface CartItemRepository: JpaRepository<CartItem, Long>