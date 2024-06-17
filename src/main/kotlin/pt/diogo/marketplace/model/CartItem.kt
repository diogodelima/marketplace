package pt.diogo.marketplace.model

import jakarta.persistence.*

@Entity
@Table(name = "cart_item")
data class CartItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val cart: Cart,

    @OneToOne
    @JoinTable(
        name = "cart_item_products",
        joinColumns = [JoinColumn(name = "cart_item_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val product: Product,

    var amount: Int

)