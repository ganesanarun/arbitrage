package com.flipper.productsource

class VisitedProductsRepository {
    private val visitedProducts = mutableSetOf<String>()

    fun hasBeenVisited(productId: String): Boolean {
        return visitedProducts.contains(productId)
    }

    fun markAsVisited(productId: String) {
        visitedProducts.add(productId)
    }
}
