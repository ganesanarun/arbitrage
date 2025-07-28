package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class Return(
    val returnId: String,
    val orderId: String,
    val productId: String,
    val reason: String,
    val status: String,
    val returnDate: Long,
    val refundAmount: Double,
    val qualityCheckResult: String
)
