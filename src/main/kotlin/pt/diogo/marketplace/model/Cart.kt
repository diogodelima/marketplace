package pt.diogo.marketplace.model

import jakarta.persistence.*

@Entity
@Table(name = "cart")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

)