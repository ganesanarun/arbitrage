package com.flipper.productsource

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.schedule

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
    val arbitrageFinder = ArbitrageFinder()
    val productRepository = ProductRepository()
    val visitedProductsRepository = VisitedProductsRepository()
    val flipkartScraper = FlipkartScraper(dataProviderClient)
    val sourcingService = SourcingService(flipkartScraper, visitedProductsRepository, arbitrageFinder, productRepository)
    val productSynchronizer = ProductSynchronizer(productRepository, dataProviderClient)

    Timer().schedule(0, 3600000) {
        runBlocking {
            productSynchronizer.synchronize()
        }
    }

    routing {
        get("/opportunities") {
            val opportunities = sourcingService.source()
            call.respond(opportunities)
        }

        post("/synchronize") {
            productSynchronizer.synchronize()
            call.respondText("Synchronization started.")
        }
    }
}
