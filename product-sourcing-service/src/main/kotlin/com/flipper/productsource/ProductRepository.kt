package com.flipper.productsource

import com.flipper.models.Product

class ProductRepository {
    private val products = mutableListOf<Product>()

    fun getAll(): List<Product> {
        return products
    }

    fun save(product: Product) {
        products.add(product)
    }

    fun update(product: Product) {
        val index = products.indexOfFirst { it.productId == product.productId }
        if (index != -1) {
            products[index] = product
        }
    }
}
