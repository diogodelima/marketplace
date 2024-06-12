package pt.diogo.marketplace.service

import org.springframework.data.domain.Sort.Direction
import pt.diogo.marketplace.model.Product

interface ProductService {

    fun save(product: Product): Product

    fun getByPage(
        page: Int,
        sortField: Product.SortField,
        direction: Direction
    ): Collection<Product>

    fun getById(id: Long): Product?

}