package cit.com

import cit.com.authentication.JwtConfig
import cit.com.plugins.configureRouting
import cit.com.plugins.configureSerializationSerialization
import cit.com.plugins.todoRouting
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

val jwtConfif = JwtConfig()

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(CallLogging) {
        level = Level.INFO
    }
    install(Authentication) {
        jwt { jwtConfif.configureKtorFeature(this) }
    }
    configureRouting()
    todoRouting()
    configureSerializationSerialization()
}
