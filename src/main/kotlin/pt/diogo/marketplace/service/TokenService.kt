package pt.diogo.marketplace.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pt.diogo.marketplace.model.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService(

    @Value("\${jwt.secret}") private val secret: String

) {

    fun generateToken(user: User): String {

        try {
            val algorithm = Algorithm.HMAC256(secret)

            return JWT.create()
                .withIssuer("marketplace")
                .withSubject(user.email)
                //.withExpiresAt(generateExpirationDate())
                .sign(algorithm)
        }catch (e: JWTCreationException){
            throw RuntimeException("Error while generating token", e)
        }

    }

    fun validateToken(token: String): String {

        try {

            val algorithm = Algorithm.HMAC256(secret)

            return JWT.require(algorithm)
                .withIssuer("marketplace")
                .build()
                .verify(token)
                .subject

        }catch (e: JWTVerificationException){
            return ""
        }

    }

    private fun generateExpirationDate(): Instant {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)
    }

}