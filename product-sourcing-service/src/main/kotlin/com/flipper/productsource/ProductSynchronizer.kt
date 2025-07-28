package com.flipper.productsource

class ProductSynchronizer(
    private val productRepository: ProductRepository,
    private val dataProviderClient: DataProviderClient
) {
    suspend fun synchronize() {
        println("Synchronizing products...")
        productRepository.getAll().forEach { product ->
            val updatedProduct = dataProviderClient.getProductData(product.productId)
            productRepository.update(updatedProduct)
            println("Updated product: ${product.productId}")
        }
        println("Synchronization complete.")
    }
}
