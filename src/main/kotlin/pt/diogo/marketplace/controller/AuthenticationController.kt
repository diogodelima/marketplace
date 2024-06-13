package pt.diogo.marketplace.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.LoginRequestDto
import pt.diogo.marketplace.dto.LoginResponseDto
import pt.diogo.marketplace.dto.RegisterRequestDto
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.service.TokenService
import pt.diogo.marketplace.service.UserService

@RestController
@RequestMapping("/auth")
class AuthenticationController(

    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val tokenService: TokenService

) {

    @PostMapping("/login")
    fun login(@RequestBody @Validated authDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val usernamePassword = UsernamePasswordAuthenticationToken(authDto.email, authDto.password)
        val auth = authenticationManager.authenticate(usernamePassword)
        val token = tokenService.generateToken(auth.principal as User)

        return ResponseEntity
            .ok(LoginResponseDto(token))
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterRequestDto): ResponseEntity<Any> {

        try {

            userService.loadUserByUsername(registerDto.email)
            return ResponseEntity.badRequest().build()

        }catch (e: UsernameNotFoundException){

            val user = User(
                firstName = registerDto.firstName,
                lastName = registerDto.lastName,
                email = registerDto.email,
                password = BCryptPasswordEncoder().encode(registerDto.password),
            )

            userService.save(user)
            return ResponseEntity
                .ok()
                .build()
        }

    }

}