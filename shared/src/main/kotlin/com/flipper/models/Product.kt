package com.flipper.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: String,
    val title: String,
    val imageUrl: String,
    val categoryPaths: List<String>,
    val mrp: Double,
    val price: Double,
    val productUrl: String,
    val description: String,
    val brand: String,
    val inStock: Boolean,
    val isAvailable: Boolean,
    val codAvailable: Boolean,
    val emiAvailable: Boolean,
    val offers: List<String>,
    val discount: Double,
    val cashBack: Double,
    val size: String,
    val color: String,
    val sizeUnit: String,
    val sizeVariants: List<String>,
    val colorVariants: List<String>,
    val styleCode: String,
    val itemRating: Double,
    val reviewCount: Int,
    val bestSellerRank: Int
)
