package pt.diogo.marketplace.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import pt.diogo.marketplace.model.Product
import pt.diogo.marketplace.service.ProductService
import java.time.LocalDate
import kotlin.test.assertEquals

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun getProductByIdThatDoestNotExistTest(){

        val productId = 4L
        `when`(productService.getById(productId)).thenReturn(null)

        val result = mockMvc
            .perform(get("/product/id/$productId"))
            .andExpect(status().isNotFound)
            .andReturn()

        assertEquals(result.response.errorMessage, "Product not found")
    }

    @Test
    fun getProductByIdThatExistTest(){

        val productId = 1L
        val product = Product(
            id = productId,
            category = Product.Category.TOYS_AND_GAMES,
            dateOfPublication = LocalDate.of(2024, 6, 12),
            description = "Jogo para PS5",
            name = "Grand Theft Auto V",
            price = 9.99
        )

        `when`(productService.getById(productId))
            .thenReturn(product)

        mockMvc
            .perform(get("/product/id/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(productId.toInt())))

    }

}