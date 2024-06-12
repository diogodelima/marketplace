package pt.diogo.marketplace.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import pt.diogo.marketplace.model.Product
import pt.diogo.marketplace.repository.ProductRepository

@Service
class ProductServiceImpl(

    private val productRepository: ProductRepository

): ProductService {

    companion object {
        const val PAGE_SIZE = 10
    }

    override fun save(product: Product): Product {
        return productRepository.save(product)
    }

    override fun getByPage(
        page: Int,
        sortField: Product.SortField,
        direction: Sort.Direction
    ): Collection<Product> {

        var sort = Sort.by(sortField.fieldName)
        sort = if (direction.isAscending) sort.ascending() else sort.descending()

        val page = PageRequest.of(page - 1, PAGE_SIZE, sort)
        return productRepository
            .findAll(page)
            .toList()

    }

    override fun getById(id: Long): Product? {
        return productRepository
            .findById(id)
            .orElse(null)
    }

}