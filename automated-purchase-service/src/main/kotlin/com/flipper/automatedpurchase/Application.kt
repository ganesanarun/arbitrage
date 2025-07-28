package com.flipper.automatedpurchase

import com.flipper.models.ArbitrageOpportunity
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
    val zincClient = ZincClient()

    routing {
        post("/order") {
            val opportunity = call.receive<ArbitrageOpportunity>()
            val order = zincClient.placeOrder(opportunity.productId, 1, opportunity.sourcePrice)
            call.respond(order)
        }
    }
}
