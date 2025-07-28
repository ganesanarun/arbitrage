package com.flipper.logisticsreturns

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
    val shippingClient = ShippingClient()

    routing {
        post("/shipment") {
            val order = call.receive<Order>()
            val shipment = shippingClient.createShipment(order)
            call.respond(shipment)
        }
    }
}
