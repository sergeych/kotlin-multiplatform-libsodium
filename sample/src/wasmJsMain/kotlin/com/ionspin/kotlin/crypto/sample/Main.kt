

import com.ionspin.kotlin.crypto.LibsodiumInitializer


fun main() {
     LibsodiumInitializer.initializeWithCallback {
//        val hash = Hash.sha512("123".encodeToUByteArray())
//        println("Hash (SHA512) of 123: ${hash.toHexString()}")
         println("Hello")
     }
}
