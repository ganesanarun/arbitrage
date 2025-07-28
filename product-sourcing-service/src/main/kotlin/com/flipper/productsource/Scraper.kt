package com.flipper.productsource

import com.flipper.models.Product

interface Scraper {
    suspend fun scrape(): List<Product>
}
