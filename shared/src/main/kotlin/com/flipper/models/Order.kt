package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val purchasePrice: Double,
    val orderDate: Long,
    val status: String,
    val paymentMethod: String,
    val shippingAddress: String,
    val billingAddress: String,
    val customerInfo: String
)
