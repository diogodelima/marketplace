package pt.diogo.marketplace.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.RegisterProductDto
import pt.diogo.marketplace.dto.ResponseProductDto
import pt.diogo.marketplace.exception.ProductNotFoundException
import pt.diogo.marketplace.model.Product
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController(

    private val productService: ProductService

) {

    @GetMapping("/{page}")
    fun getByPage(
        @PathVariable("page") page: Int,
        @RequestParam("field") field: Product.SortField = Product.SortField.NAME,
        @RequestParam("direction") direction: Direction = Direction.DESC
    ): ResponseEntity<Collection<Product>> {
        return ResponseEntity
            .ok(productService.getByPage(page, field, direction))
    }

    @GetMapping("/id/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<Product>{
        val product = productService.getById(id) ?: throw ProductNotFoundException()
        return ResponseEntity
            .ok(product)
    }

    @PutMapping("/register")
    fun registerProduct(@RequestBody @Valid productDto: RegisterProductDto): ResponseEntity<ResponseProductDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as User

        var product = Product(
            name = productDto.name,
            description = productDto.description,
            price = productDto.price,
            category = productDto.category,
            user = user
        )

        product = productService.save(product)
        return ResponseEntity
            .ok(ResponseProductDto(
                product.id!!, product.name, product.description, product.price,
                product.category, product.dateOfPublication
            ))
    }

}