package pt.diogo.marketplace.controller

import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val myToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtYXJrZXRwbGFjZSIsInN1YiI6ImRpb2dvbGltYUBnbWFpbC5jb20ifQ.e26MC_GAzW34Z0GbMBa2OkqeXZ9G4yXZ0ROHjIdSUSA"

    @Test
    fun getProductsSortedTest(){

        mockMvc
            .perform(get("/product/1"))
            .andExpect(status().isOk)
            .andExpect((jsonPath("$[0].id", `is`(1))))

    }

    @Test
    fun getProductById() {

        val productId = 1L

        mockMvc
            .perform(get("/product/id/$productId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(productId.toInt())))

    }

    @Test
    fun registerProduct() {

        val json = """
            {
                "name": "Rocket League",
                "description": "Um jogo que mistura futebol e carros! \nUm jogo perfeito para os amantes destes temas.",
                "price": 19.99,
                "category": [
                    "ALL",
                    "TOYS_AND_GAMES"
                ]
            }
        """.trimIndent()

        mockMvc
            .perform(
                put("/product/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isForbidden)

        mockMvc
            .perform(
                put("/product/register")
                    .header("Authorization", "Bearer $myToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.dateOfPublication", `is`(LocalDate.now().toString())))

    }

}