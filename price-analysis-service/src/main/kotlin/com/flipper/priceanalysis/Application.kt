package com.flipper.priceanalysis

import com.flipper.models.Product
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}

fun Application.configureRouting() {
    val priceAnalyzer = PriceAnalyzer()
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    routing {
        get("/analyze/{platform}/{productId}") {
            val platform = call.parameters["platform"] ?: return@get call.respondText("Missing platform")
            val productId = call.parameters["productId"] ?: return@get call.respondText("Missing productId")

            val product: Product = client.get("http://product-sourcing-service:8081/product/$platform/$productId").body()
            val opportunity = priceAnalyzer.analyze(product)
            call.respond(opportunity)
        }
    }
}
