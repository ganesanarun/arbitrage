package com.flipper.productsource

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}

fun Application.configureRouting() {
    val apiKey = environment.config.propertyOrNull("ktor.deployment.apiKey")?.getString() ?: "default_api_key"
    val dataProviderClient = DataProviderClient(apiKey)

    routing {
        get("/product/{platform}/{productId}") {
            val platform = call.parameters["platform"] ?: return@get call.respondText("Missing platform")
            val productId = call.parameters["productId"] ?: return@get call.respondText("Missing productId")

            try {
                val product = dataProviderClient.getProductData(productId)
                call.respond(product)
            } catch (e: Exception) {
                call.respondText("Error: ${e.message}")
            }
        }
    }
}
