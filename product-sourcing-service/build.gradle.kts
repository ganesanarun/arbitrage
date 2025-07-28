plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":shared"))
    implementation("io.ktor:ktor-server-core-jvm:2.3.10")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.10")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.ktor:ktor-client-core-jvm:2.3.10")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.10")
    implementation("io.ktor:ktor-client-content-negotiation-jvm:2.3.10")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.10")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.10")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.23")
    testImplementation("io.mockk:mockk:1.13.10")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
