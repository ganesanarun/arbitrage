package com.arbitrage.scraper

import com.arbitrage.data.Product
import org.jsoup.Jsoup
import java.util.regex.Pattern

class FlipkartScraper : Scraper {
    override suspend fun scrape(category: String): List<Product> {
        val url = "https://www.flipkart.com/search?q=$category"
        val doc = Jsoup.connect(url).get()
        val products = mutableListOf<Product>()

        val productElements = doc.select("._1AtVbE")

        for (productElement in productElements) {
            val name = productElement.select("._4rR01T").text()
            val price = productElement.select("._30jeq3").text().replace("₹", "").replace(",", "").toDoubleOrNull()
            val productUrl = productElement.select("._1fQZEK").attr("abs:href")
            val imageUrl = productElement.select("._396cs4").attr("src")

            if (name.isNotEmpty() && price != null && productUrl.isNotEmpty() && imageUrl.isNotEmpty()) {
                try {
                    val productDoc = Jsoup.connect(productUrl).get()
                    val deliveryInfo = productDoc.select("._12-mGv").text()
                    val pincode = extractPincode(deliveryInfo)
                    val deliveryTime = extractDeliveryTime(deliveryInfo)

                    if (pincode != null && deliveryTime != null) {
                        products.add(
                            Product(
                                name = name,
                                price = price,
                                source = "Flipkart",
                                url = productUrl,
                                imageUrl = imageUrl,
                                sourceOriginPincode = pincode,
                                estimatedDeliveryTime = deliveryTime
                            )
                        )
                    }
                } catch (e: Exception) {
                    // Log the exception
                    println("Error scraping product page $productUrl: ${e.message}")
                }
            }
        }
        return products
    }

    private fun extractPincode(deliveryInfo: String): String? {
        val pattern = Pattern.compile("\\b\\d{6}\\b")
        val matcher = pattern.matcher(deliveryInfo)
        return if (matcher.find()) {
            matcher.group(0)
        } else {
            null
        }
    }

    private fun extractDeliveryTime(deliveryInfo: String): Int? {
        val pattern = Pattern.compile("(\\d+)(?=\\s*Days)")
        val matcher = pattern.matcher(deliveryInfo)
        return if (matcher.find()) {
            matcher.group(1).toIntOrNull()
        } else {
            null
        }
    }
}
