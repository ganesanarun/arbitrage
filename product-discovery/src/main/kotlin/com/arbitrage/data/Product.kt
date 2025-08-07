package com.arbitrage.data

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val price: Double,
    val source: String,
    val url: String,
    val imageUrl: String,
    val sourceOriginPincode: String,
    val estimatedDeliveryTime: Int // in days
)
