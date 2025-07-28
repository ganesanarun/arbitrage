package com.flipper.sellerdashboard

import com.flipper.models.Order
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import com.flipper.models.ArbitrageOpportunity
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }

    routing {
        get("/") {
            val opportunities: List<ArbitrageOpportunity> = client.get("http://product-sourcing-service:8081/opportunities").body()

            call.respondHtml {
                head {
                    title("Seller Dashboard")
                }
                body {
                    h1 { +"Arbitrage Opportunities" }
                    table {
                        thead {
                            tr {
                                th { +"Product ID" }
                                th { +"Source Platform" }
                                th { +"Source Price" }
                                th { +"Resale Platform" }
                                th { +"Resale Price" }
                                th { +"Estimated Profit" }
                                th { +"Status" }
                            }
                        }
                        tbody {
                            opportunities.forEach { opportunity ->
                                tr {
                                    td { +opportunity.productId }
                                    td { +opportunity.sourcePlatform }
                                    td { +opportunity.sourcePrice.toString() }
                                    td { +opportunity.resalePlatform }
                                    td { +opportunity.resalePrice.toString() }
                                    td { +opportunity.netProfit.toString() }
                                    td { +"In Stock" }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
