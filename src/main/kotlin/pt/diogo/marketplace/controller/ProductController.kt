package pt.diogo.marketplace.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.exception.ProductNotFoundException
import pt.diogo.marketplace.model.Product
import pt.diogo.marketplace.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController(

    @Autowired
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
        return ResponseEntity.ok(product)
    }

}