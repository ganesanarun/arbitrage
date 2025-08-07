package com.arbitrage

import com.arbitrage.data.DatabaseFactory
import com.arbitrage.data.ProductService
import com.arbitrage.scraper.ScrapingService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init()
    val productService = ProductService()
    val scrapingService = ScrapingService(productService)

    launch {
        scrapingService.start()
    }

    routing {
        get("/products") {
            call.respond(productService.getAll())
        }
    }
}
