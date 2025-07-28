package com.flipper.productsource

import com.flipper.models.ArbitrageOpportunity

class SourcingService(
    private val scraper: Scraper,
    private val visitedProductsRepository: VisitedProductsRepository,
    private val arbitrageFinder: ArbitrageFinder,
    private val productRepository: ProductRepository
) {
    suspend fun source(): List<ArbitrageOpportunity> {
        val products = scraper.scrape()
        val newProducts = products.filter { !visitedProductsRepository.hasBeenVisited(it.productId) }
        newProducts.forEach {
            productRepository.save(it)
            visitedProductsRepository.markAsVisited(it.productId)
        }
        return arbitrageFinder.findOpportunities(newProducts)
    }
}
