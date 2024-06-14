package pt.diogo.marketplace.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "product")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    val price: Double,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val category: Category,

    @Column(name = "date_of_publication", nullable = false)
    val dateOfPublication: LocalDate = LocalDate.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    val user: User

){

    enum class Category(

        val displayName: String

    ) {

        VEHICLES("Veículos"),
        TOYS_AND_GAMES("Brinquedos e Jogos"),
        ELECTRONICS("Equipamento Eletrónico"),
        ENTERTAINMENT("Entretenimento"),
        SPORT("Desporto"),
        PETS("Animais de Estimação"),
        CLOTHING("Vestuário")

    }

    enum class SortField(

        val fieldName: String

    ){

        NAME("name"),
        PRICE("price"),
        DATE_OF_PUBLICATION("dateOfPublication")

    }

}