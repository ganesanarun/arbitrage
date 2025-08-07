package com.arbitrage.scraper

import com.arbitrage.data.ProductService
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

class ScrapingService(private val productService: ProductService) {

    private val scrapers = listOf(
        AmazonScraper(),
        FlipkartScraper()
    )

    private val categories = listOf("laptop", "mobile", "tv", "washing machine")

    suspend fun start() {
        while (coroutineContext.isActive) {
            categories.forEach { category ->
                scrapers.forEach { scraper ->
                    try {
                        val products = scraper.scrape(category)
                        products.forEach { product ->
                            if (product.estimatedDeliveryTime <= 2) {
                                productService.create(product)
                            }
                        }
                    } catch (e: Exception) {
                        // Log the exception
                        println("Error scraping ${scraper.javaClass.simpleName} for category $category: ${e.message}")
                    }
                }
            }
            delay(1000 * 60 * 60) // Scrape every hour
        }
    }
}
