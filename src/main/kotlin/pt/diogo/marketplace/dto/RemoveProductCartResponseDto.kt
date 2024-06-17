package pt.diogo.marketplace.dto

import pt.diogo.marketplace.model.Product
import java.time.LocalDate

class RemoveProductCartResponseDto(

    val productId: Long,
    val amount: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: Collection<Product.Category>,
    val dateOfPublication: LocalDate

)