package pt.diogo.marketplace.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

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

    override fun hashCode(): Int {
        return Objects.hash(email)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as User
        return email == other.email
    }

    enum class Role {

        USER,
        ADMIN

    }

}