package pt.diogo.marketplace.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
data class User(

    @Id
    val email: String,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    @get:JvmName("getUserPassword")
    var password: String,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,

    @OneToOne(fetch = FetchType.LAZY)
    val cart: Cart

): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    enum class Role {

        USER,
        ADMIN

    }

}