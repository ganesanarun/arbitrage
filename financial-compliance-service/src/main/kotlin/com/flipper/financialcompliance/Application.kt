package com.flipper.financialcompliance

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
    val profitCalculator = ProfitCalculator()

    routing {
        post("/profit") {
            val order = call.receive<Order>()
            val resalePrice = call.request.queryParameters["resalePrice"]?.toDoubleOrNull() ?: 0.0
            val profit = profitCalculator.calculateProfit(order, resalePrice)
            call.respond(mapOf("profit" to profit))
        }
    }
}
