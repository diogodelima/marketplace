package pt.diogo.marketplace.controller

import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun getProductsSortedTest(){

        mockMvc
            .perform(get("/product/1"))
            .andExpect(status().isOk)
            .andExpect((jsonPath("$[0].id", `is`(3))))

    }

    @Test
    fun getProductById() {

        val productId = 1L

        mockMvc.perform(get("/product/id/$productId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(productId.toInt())))

    }

}