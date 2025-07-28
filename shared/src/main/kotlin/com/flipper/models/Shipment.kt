package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class Shipment(
    val shipmentId: String,
    val orderId: String,
    val trackingNumber: String,
    val carrierId: String,
    val pickupPincode: String,
    val dropPincode: String,
    val estimatedDeliveryDate: Long,
    val actualDeliveryDate: Long,
    val statusUpdates: List<String>
)
