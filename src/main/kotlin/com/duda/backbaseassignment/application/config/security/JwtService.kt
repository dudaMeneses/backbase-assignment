package com.duda.backbaseassignment.application.config.security

import com.duda.backbaseassignment.application.exception.InvalidJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.stereotype.Service

@Service
class JwtService {

    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    fun createToken(id: Long): String {
        val jws = Jwts.builder()
            .claim("moviesId", id)
            .signWith(key)
            .compact()

        return Base64.encodeBase64String(jws.toByteArray())
    }

    fun getUserId(base64Token: String): Int {
        try {
            val jws = String(Base64.decodeBase64(base64Token))
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)

            return claims.body["moviesId"] as Int
        } catch (jwtException: JwtException) {
            throw InvalidJwtException()
        }
    }
}
