package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class PriceSnapshot(
    val productId: String,
    val platformId: String,
    val timestamp: Long,
    val price: Double,
    val discount: Double,
    val stockStatus: String,
    val sellerInfo: String
)
