package pt.diogo.marketplace.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.ProductResponseDto
import pt.diogo.marketplace.dto.RegisterProductDto
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
        @RequestParam("direction") direction: Direction = Direction.DESC,
        @RequestParam("category") category: Product.Category = Product.Category.ALL
    ): ResponseEntity<Collection<ProductResponseDto>> {
        return ResponseEntity
            .ok(
                productService
                    .getByPage(page, field, direction, category)
                    .map { ProductResponseDto(it.id!!, it.name, it.description,
                        it.price, it.category, it.dateOfPublication) }
            )
    }

    @GetMapping("/id/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<ProductResponseDto>{
        val product = productService.getById(id) ?: throw ProductNotFoundException()
        return ResponseEntity
            .ok(ProductResponseDto(product.id!!, product.name, product.description, product.price,
                product.category, product.dateOfPublication))
    }

    @PostMapping("/register")
    fun registerProduct(@RequestBody @Valid productDto: RegisterProductDto): ResponseEntity<ProductResponseDto> {

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
            .ok(ProductResponseDto(
                product.id!!, product.name, product.description, product.price,
                product.category, product.dateOfPublication
            ))
    }

}