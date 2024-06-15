package pt.diogo.marketplace.dto


import jakarta.persistence.Enumerated
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import pt.diogo.marketplace.model.Product

class RegisterProductDto(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:NotNull
    val price: Double,

    @field:Enumerated
    val category: Collection<Product.Category>

)