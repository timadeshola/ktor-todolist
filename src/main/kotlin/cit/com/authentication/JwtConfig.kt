package cit.com.authentication

import cit.com.model.JwtResponse
import cit.com.model.JwtUser
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtConfig() {
    companion object Constants {
        private val issuer =  "jwt.issuer"
        private val audience = "jwt.audience"
        private val myRealm = "jwt.realm"
    }

    private val jwtAlgorithm = Algorithm.HMAC256("secret")

    private val jwtVerified: JWTVerifier = JWT.require(jwtAlgorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()


    fun generateToken(user: JwtUser): JwtResponse  {
        val date = Date(System.currentTimeMillis() + 60000)
        return JwtResponse(
            token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withClaim("id", user.id)
                .withExpiresAt(date)
                .sign(jwtAlgorithm),
            expiredIn = date.time,
            username = user.username
        )
    }

    fun configureKtorFeature(config: JWTAuthenticationProvider.Config) = with(config) {
        verifier(jwtVerified)
        realm = myRealm
        validate {
            val userId = it.payload.getClaim("id").asInt()
            val username = it.payload.getClaim("username").asString()
            if (userId != null && username != null) {
                JwtUser(userId, username)
            } else {
                null
            }
        }
    }
}
