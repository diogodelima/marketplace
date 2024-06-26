package pt.diogo.marketplace.repository

import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.diogo.marketplace.model.Product

@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    fun findAllByCategory(pageRequest: PageRequest, category: Product.Category): Collection<Product>

}