package com.flipper.productsource

import com.flipper.models.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SourcingServiceTest {
    @Test
    fun testSource() = runBlocking {
        val scraper = mockk<Scraper>()
        val visitedProductsRepository = VisitedProductsRepository()
        val arbitrageFinder = ArbitrageFinder()
        val productRepository = ProductRepository()
        val sourcingService = SourcingService(scraper, visitedProductsRepository, arbitrageFinder, productRepository)

        coEvery { scraper.scrape() } returns listOf(
            Product("1", "Product 1", "", emptyList(), 50.0, 50.0, "", "", "", true, true, true, true, emptyList(), 0.0, 0.0, "", "", "", emptyList(), emptyList(), "", 0.0, 0, 0),
            Product("2", "Product 2", "", emptyList(), 150.0, 150.0, "", "", "", true, true, true, true, emptyList(), 0.0, 0.0, "", "", "", emptyList(), emptyList(), "", 0.0, 0, 0)
        )

        val opportunities = sourcingService.source()
        assertEquals(1, opportunities.size)
        assertEquals("1", opportunities[0].productId)
    }
}
