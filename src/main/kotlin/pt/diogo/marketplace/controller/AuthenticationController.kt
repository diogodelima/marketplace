package pt.diogo.marketplace.controller

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.diogo.marketplace.dto.*
import pt.diogo.marketplace.exception.PasswordNotMatchException
import pt.diogo.marketplace.exception.UserCurrentPasswordIsNotEqualsException
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.service.MailSenderService
import pt.diogo.marketplace.service.TokenService
import pt.diogo.marketplace.service.UserService

@RestController
@RequestMapping("/auth")
class AuthenticationController(

    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val tokenService: TokenService,
    private val mailSenderService: MailSenderService

) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid requestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val usernamePassword = UsernamePasswordAuthenticationToken(requestDto.email, requestDto.password)
        val auth = authenticationManager.authenticate(usernamePassword)
        val token = tokenService.generateToken(auth.principal as User)

        return ResponseEntity
            .ok(LoginResponseDto(token))
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid requestDto: RegisterRequestDto): ResponseEntity<Any> {

        try {

            userService.loadUserByUsername(requestDto.email)
            return ResponseEntity
                .badRequest()
                .body("Email already exists")

        }catch (e: UsernameNotFoundException){

            val user = User(
                firstName = requestDto.firstName,
                lastName = requestDto.lastName,
                email = requestDto.email,
                password = BCryptPasswordEncoder().encode(requestDto.password),
            )

            userService.save(user)
            return ResponseEntity
                .ok()
                .build()
        }catch (e: ConstraintViolationException) {
            return ResponseEntity
                .badRequest()
                .body(e.constraintViolations.map { it.message })
        }

    }

    @PutMapping("changepassword")
    fun changePassword(@RequestBody @Valid requestDto: ChangePasswordRequestDto): ResponseEntity<ChangePasswordResponseDto> {

        val user = SecurityContextHolder.getContext().authentication.principal as User
        val encoder = BCryptPasswordEncoder()

        if (!encoder.matches(requestDto.currentPassword, user.password))
            throw UserCurrentPasswordIsNotEqualsException()

        if (requestDto.newPassword != requestDto.confirmNewPassword)
            throw PasswordNotMatchException()

        user.password = encoder.encode(requestDto.newPassword)
        userService.save(user)
        return ResponseEntity
            .ok(ChangePasswordResponseDto(("Your password was changed successfully")))
    }

}