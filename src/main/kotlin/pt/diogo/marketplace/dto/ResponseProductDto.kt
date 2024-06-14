package pt.diogo.marketplace.dto

import pt.diogo.marketplace.model.Product
import java.time.LocalDate

class ResponseProductDto(

    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val category: Product.Category,
    val dateOfPublication: LocalDate

)