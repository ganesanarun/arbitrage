package com.flipper.inventorylisting

import com.flipper.models.Order
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}

fun Application.configureRouting() {
    val inventoryManager = InventoryManager()

    routing {
        post("/inventory/add") {
            val order = call.receive<Order>()
            inventoryManager.addProduct(order.productId, order.quantity)
            call.respondText("Inventory updated")
        }

        get("/inventory") {
            call.respond(inventoryManager.getInventory())
        }
    }
}
