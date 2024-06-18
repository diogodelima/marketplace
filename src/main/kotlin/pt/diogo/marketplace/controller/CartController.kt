package pt.diogo.marketplace.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.*
import pt.diogo.marketplace.exception.ProductNotFoundException
import pt.diogo.marketplace.model.CartItem
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.service.CartItemService
import pt.diogo.marketplace.service.CartService
import pt.diogo.marketplace.service.ProductService

@RestController
@RequestMapping("/cart")
class CartController(

    private val cartService: CartService,
    val cartItemService: CartItemService,
    private val productService: ProductService

) {

    @GetMapping("/list")
    fun listProducts(): ResponseEntity<Collection<CartItemResponseDto>> {

        val user = SecurityContextHolder.getContext().authentication.principal as User
        val cart = user.cart
        val products = cart.cartItems
            .map {
                val productResponse = ProductResponseDto(it.product.id!!, it.product.name, it.product.description,
                    it.product.price, it.product.category, it.product.dateOfPublication)
                CartItemResponseDto(productResponse, it.amount)
            }

        return ResponseEntity.ok(products)
    }

    @PostMapping("/add")
    fun addProduct(@RequestBody @Valid requestDto: AddProductCartRequestDto): ResponseEntity<CartItemResponseDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as User
        val cart = user.cart
        val product = productService.getById(requestDto.productId) ?: throw ProductNotFoundException()
        val amount = requestDto.amount
        var cartItem = cart.cartItems.find { it.product.id == requestDto.productId }

        if (cartItem != null) {
            cartItem.amount += amount
        } else {
            cartItem = CartItem(cart = cart, product = product, amount = amount)
            cart.cartItems.add(cartItem)
        }

        cartItemService.save(cartItem)
        cartService.save(cart)

        val productResponse = ProductResponseDto(product.id!!, product.name,
            product.description, product.price, product.category, product.dateOfPublication)
        val cartItemResponse = CartItemResponseDto(productResponse, cartItem.amount)

        return ResponseEntity.ok(cartItemResponse)
    }

    @PutMapping("/remove")
    fun removeProduct(@RequestBody @Valid requestDto: RemoveProductCartRequestDto): ResponseEntity<CartItemResponseDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as User
        val cart = user.cart
        val product = productService.getById(requestDto.productId) ?: throw ProductNotFoundException()
        val amount = requestDto.amount
        val productResponse = ProductResponseDto(product.id!!, product.name,
            product.description, product.price, product.category, product.dateOfPublication)
        val cartItem = cart.cartItems.find { it.product.id == requestDto.productId } ?: CartItem(cart = cart, product = product, amount = 0)

        cartItem.amount -= amount

        if (cartItem.amount <= 0) {
            cart.cartItems.remove(cartItem)
            cartItem.amount = 0
            cartService.save(cart)
            cartItem.id?.let { cartItemService.delete(it) }
        }else cartItemService.save(cartItem)

        return ResponseEntity.ok(CartItemResponseDto(productResponse, cartItem.amount))
    }

}