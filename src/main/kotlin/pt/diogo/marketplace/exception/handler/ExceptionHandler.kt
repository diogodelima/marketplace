package pt.diogo.marketplace.exception.handler

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.diogo.marketplace.exception.PasswordNotMatchException
import pt.diogo.marketplace.exception.ProductNotFoundException
import pt.diogo.marketplace.exception.UserCurrentPasswordIsNotEqualsException

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(ProductNotFoundException::class)
    fun productNotFoundExceptionHandle(ex: ProductNotFoundException, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "Product not found", HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(UserCurrentPasswordIsNotEqualsException::class)
    fun userCurrentPasswordIsNotEqualsExceptionHandle(ex: UserCurrentPasswordIsNotEqualsException, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "The current password is not equals as the user password", HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(PasswordNotMatchException::class)
    fun passwordNotMatchExceptionHandler(ex: PasswordNotMatchException, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "The passwords you entered don't match", HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}