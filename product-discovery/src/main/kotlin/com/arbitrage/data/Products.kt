package com.arbitrage.data

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val source = varchar("source", 255)
    val url = varchar("url", 255)
    val imageUrl = varchar("imageUrl", 255)
    val sourceOriginPincode = varchar("sourceOriginPincode", 255)
    val estimatedDeliveryTime = integer("estimatedDeliveryTime")

    override val primaryKey = PrimaryKey(id)
}
