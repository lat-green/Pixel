package com.example.pixel.server.authorization

import io.jsonwebtoken.io.Encoders
import java.security.SecureRandom

object GenerateJWTSecrets {

	@JvmStatic
	fun main(args: Array<String>) {
		val length = args.getOrNull(0)?.toInt() ?: 64
		val random = SecureRandom()
		val buffer = ByteArray(length)
		random.nextBytes(buffer)
		println("jwt.secret.access=" + Encoders.BASE64.encode(buffer))
		random.nextBytes(buffer)
		println("jwt.secret.refresh=" + Encoders.BASE64.encode(buffer))
	}
}
