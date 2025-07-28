package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class ArbitrageOpportunity(
    val productId: String,
    val sourcePlatform: String,
    val sourcePrice: Double,
    val resalePlatform: String,
    val resalePrice: Double,
    val estimatedProfitMargin: Double,
    val shippingCostEstimate: Double,
    val taxesEstimate: Double,
    val totalCost: Double,
    val netProfit: Double,
    val recommendationTimestamp: Long
)
