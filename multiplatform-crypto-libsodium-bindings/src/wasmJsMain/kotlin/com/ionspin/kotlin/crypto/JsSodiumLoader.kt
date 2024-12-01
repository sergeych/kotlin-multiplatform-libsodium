package ext.libsodium.com.ionspin.kotlin.crypto

import com.ionspin.kotlin.crypto.getSodiumLoaded
import com.ionspin.kotlin.crypto.sodiumLoaded
import ext.libsodium._libsodiumPromise
import ext.libsodium.crypto_generichash
import ext.libsodium.crypto_hash_sha256
import ext.libsodium.crypto_hash_sha256_init
import ext.libsodium.crypto_hash_sha512
import ext.libsodium.sodium_init
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Ugljesa Jovanovic
 * ugljesa.jovanovic@ionspin.com
 * on 27-May-2020
 */
object JsSodiumLoader {

    class _EmitJsSodiumFunction {
        init {
            println(::crypto_generichash)
            println(::crypto_hash_sha256)
            println(::crypto_hash_sha512)
            println(::crypto_hash_sha256_init)
        }

    }

    // TODO: попробовать сделать из этого suspend вместо continuation
    suspend fun load(): Unit = suspendCoroutine { continuation ->
        if (!getSodiumLoaded()) {
            _libsodiumPromise.then<JsAny?> {
                sodium_init()
                sodiumLoaded = true
                //Dynamic может быть Юнит, но Unit не может быть JsAny?
                continuation.resumeWith(Result.success(Unit))
                null
            }.catch { e ->
                val throwable = e as? Throwable
                if (throwable != null) {
                    continuation.resumeWith(Result.failure(throwable))
                } else {
                    continuation.resumeWith(Result.failure(Exception("Error: $e")))
                }
                null
            }
        } else {
            continuation.resumeWith(Result.success(Unit))
        }
    }

    fun loadWithCallback(doneCallback: () -> (JsAny)) {
        if (!getSodiumLoaded()) {
            _libsodiumPromise.then {
                sodium_init()
                sodiumLoaded = true
                doneCallback.invoke()
            }
        } else {
            doneCallback.invoke()
        }
    }
}
