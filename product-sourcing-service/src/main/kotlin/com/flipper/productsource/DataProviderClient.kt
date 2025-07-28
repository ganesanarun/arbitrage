package com.flipper.productsource

import com.flipper.models.Product
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class DataProviderClient(private val apiKey: String) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getProductData(productId: String): Product {
        val response: OxylabsProductResponse = client.post("https://realtime.oxylabs.io/v1/queries") {
            contentType(ContentType.Application.Json)
            setBody(
                OxylabsProductRequest(
                    source = "amazon_product",
                    query = productId,
                    domain = "in"
                )
            )
        }.body()

        val result = response.results.first()
        val content = result.content
        return Product(
            productId = content.asin,
            title = content.title,
            imageUrl = content.images.first(),
            categoryPaths = content.category,
            mrp = content.price_upper,
            price = content.price,
            productUrl = content.url,
            description = content.description,
            brand = content.brand,
            inStock = content.available,
            isAvailable = content.available,
            codAvailable = false, // Not available in Oxylabs response
            emiAvailable = false, // Not available in Oxylabs response
            offers = emptyList(), // Not available in Oxylabs response
            discount = 0.0, // Not available in Oxylabs response
            cashBack = 0.0, // Not available in Oxylabs response
            size = "", // Not available in Oxylabs response
            color = "", // Not available in Oxylabs response
            sizeUnit = "", // Not available in Oxylabs response
            sizeVariants = emptyList(), // Not available in Oxylabs response
            colorVariants = emptyList(), // Not available in Oxylabs response
            styleCode = "", // Not available in Oxylabs response
            itemRating = content.rating,
            reviewCount = content.reviews_count,
            bestSellerRank = 0 // Not available in Oxylabs response
        )
    }
}

@kotlinx.serialization.Serializable
data class OxylabsProductRequest(
    val source: String,
    val query: String,
    val domain: String
)

@kotlinx.serialization.Serializable
data class OxylabsProductResponse(
    val results: List<OxylabsProductResult>
)

@kotlinx.serialization.Serializable
data class OxylabsProductResult(
    val content: OxylabsProductContent
)

@kotlinx.serialization.Serializable
data class OxylabsProductContent(
    val asin: String,
    val title: String,
    val images: List<String>,
    val category: List<String>,
    val price_upper: Double,
    val price: Double,
    val url: String,
    val description: String,
    val brand: String,
    val available: Boolean,
    val rating: Double,
    val reviews_count: Int
)
