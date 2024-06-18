package pt.diogo.marketplace.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cart_item")
data class CartItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val cart: Cart,

    @OneToOne
    val product: Product,

    var amount: Int

){

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as CartItem
        return id == other.id
    }

}