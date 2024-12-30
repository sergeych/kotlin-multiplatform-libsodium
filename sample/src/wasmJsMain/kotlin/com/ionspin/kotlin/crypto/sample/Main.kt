

import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.getSodium
import com.ionspin.kotlin.crypto.hash.Hash
import com.ionspin.kotlin.crypto.util.encodeToUByteArray
import ext.libsodium.com.ionspin.kotlin.crypto.JsSodiumInterface


fun main() {
    LibsodiumInitializer.initializeWithCallback {
        val hash = Hash.sha512("123".encodeToUByteArray())
        println("Hash (SHA512) of 123: ${hash.toHexString()}")
    }
}
