package pt.diogo.marketplace.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pt.diogo.marketplace.model.User
import pt.diogo.marketplace.repository.UserRepository

@Service
class UserServiceImpl(

    private val userRepository: UserRepository

): UserService {

    override fun loadUserByUsername(username: String?): UserDetails {

        if (username == null)
            throw UsernameNotFoundException("Username can not be null")

        return userRepository.findById(username).orElseThrow { UsernameNotFoundException("Username not found: $username") }
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

}