package pt.diogo.marketplace.service

import org.springframework.security.core.userdetails.UserDetailsService
import pt.diogo.marketplace.model.User

interface UserService: UserDetailsService {

    fun save(user: User)

}