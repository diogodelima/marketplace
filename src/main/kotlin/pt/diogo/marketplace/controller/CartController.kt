package pt.diogo.marketplace.controller

import jakarta.validation.Valid
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.AddProductCartRequestDto
import pt.diogo.marketplace.dto.AddProductCartResponseDto
import pt.diogo.marketplace.exception.LoginException
import pt.diogo.marketplace.exception.ProductNotFoundException
import pt.diogo.marketplace.model.CartItem
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.service.CartItemService
import pt.diogo.marketplace.service.ProductService

@RestController
@RequestMapping("/cart")
class CartController(

    private val cartItemService: CartItemService,
    private val productService: ProductService

) {

    @PostMapping("/add")
    fun addProduct(@RequestBody @Valid requestDto: AddProductCartRequestDto): ResponseEntity<AddProductCartResponseDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as User
        val cart = user.cart
        val product = productService.getById(requestDto.productId) ?: throw ProductNotFoundException()
        val amount = requestDto.amount
        var cartItem = cartItemService.getCartItemByCartId(cart.id!!)
            .find { it.product.id == product.id } ?: CartItem(cart = cart, product = product, amount = 0)

        cartItem.amount += amount
        cartItemService.save(cartItem)

        return ResponseEntity.ok(AddProductCartResponseDto(product.id!!, cartItem.amount, product.name,
            product.description, product.price, product.category, product.dateOfPublication))
    }

}