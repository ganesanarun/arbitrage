package com.flipper.sellerdashboard

import com.flipper.models.Order
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
            json()
        }
    }

    routing {
        get("/") {
            val orders: Map<String, Int> = client.get("http://inventory-listing-service:8084/inventory").body()

            call.respondHtml {
                head {
                    title("Seller Dashboard")
                }
                body {
                    h1 { +"Seller Dashboard" }
                    table {
                        thead {
                            tr {
                                th { +"Product ID" }
                                th { +"Quantity" }
                            }
                        }
                        tbody {
                            orders.forEach { (productId, quantity) ->
                                tr {
                                    td { +productId }
                                    td { +quantity.toString() }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
