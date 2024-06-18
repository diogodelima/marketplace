package pt.diogo.marketplace.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cart")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToMany(fetch = FetchType.EAGER)
    val cartItems: MutableSet<CartItem> = mutableSetOf()

){

    fun getTotal(): Double {
        return cartItems.sumOf {
            it.amount * it.product.price
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Cart
        return id == other.id
    }

}