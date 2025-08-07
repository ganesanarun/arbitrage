package com.arbitrage.scraper

import com.arbitrage.data.Product

interface Scraper {
    suspend fun scrape(category: String): List<Product>
}
