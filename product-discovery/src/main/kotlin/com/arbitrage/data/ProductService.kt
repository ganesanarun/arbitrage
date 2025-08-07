package com.arbitrage.data

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProductService {

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(product: Product) {
        dbQuery {
            Products.insert {
                it[name] = product.name
                it[price] = product.price
                it[source] = product.source
                it[url] = product.url
                it[imageUrl] = product.imageUrl
                it[sourceOriginPincode] = product.sourceOriginPincode
                it[estimatedDeliveryTime] = product.estimatedDeliveryTime
            }
        }
    }

    suspend fun getAll(): List<Product> {
        return dbQuery {
            Products.selectAll().map { toProduct(it) }
        }
    }

    private fun toProduct(row: ResultRow): Product =
        Product(
            name = row[Products.name],
            price = row[Products.price],
            source = row[Products.source],
            url = row[Products.url],
            imageUrl = row[Products.imageUrl],
            sourceOriginPincode = row[Products.sourceOriginPincode],
            estimatedDeliveryTime = row[Products.estimatedDeliveryTime]
        )
}
