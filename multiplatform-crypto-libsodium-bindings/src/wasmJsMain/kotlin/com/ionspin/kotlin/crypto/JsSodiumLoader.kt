package ext.libsodium.com.ionspin.kotlin.crypto

import com.ionspin.kotlin.crypto.getSodiumLoaded
import com.ionspin.kotlin.crypto.sodiumLoaded
import ext.libsodium.*
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
                val throwable = e.toThrowableOrNull()
                if (throwable != null) {
                    continuation.resumeWith(Result.failure(throwable))
                } else {
                    continuation.resumeWith(Result.failure(Throwable("Unknown error", throwable)))
                }
                null
            }
        } else {
            continuation.resumeWith(Result.success(Unit))
        }
    }

    fun loadWithCallback(doneCallback: () -> (Unit)) {
        if (!getSodiumLoaded()) {
            _libsodiumPromise.then<JsAny?> {
                sodium_init()
                sodiumLoaded = true
                doneCallback.invoke()
                null
            }
        } else {
            doneCallback.invoke()
        }
    }
}
