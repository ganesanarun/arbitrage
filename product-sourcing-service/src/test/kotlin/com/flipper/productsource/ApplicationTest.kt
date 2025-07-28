package com.flipper.productsource

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testGetProduct() = testApplication {
        environment {
            config = MapApplicationConfig("ktor.deployment.apiKey" to "test_api_key")
        }
        application {
            module()
        }
        client.get("/product/amazon/123").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertNotNull(bodyAsText())
        }
    }
}
