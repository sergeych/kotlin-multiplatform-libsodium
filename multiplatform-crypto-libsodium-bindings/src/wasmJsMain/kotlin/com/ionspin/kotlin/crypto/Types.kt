package ext.libsodium.com.ionspin.kotlin.crypto


import org.khronos.webgl.Uint8Array

//TODO: может быть стоит поудалять ненужное

external object SignatureStateType: JsAny

external object Chacha20poly1305EncryptDetachedResult : JsAny {
    val ciphertext: Uint8Array
    var mac: Uint8Array
}

external object CryptoBoxDetachedResult : JsAny {
    val ciphertext: Uint8Array
    var mac: Uint8Array
}

//external object CryptoBoxKeypairResult: JsAny {
//    val publicKey: Uint8Array
//    val privateKey: Uint8Array
//}

external object CryptoKxClientSessionKeysResult: JsAny {
    val sharedRx: Uint8Array
    val sharedTx: Uint8Array
}

//external object CryptoKxKeypairResult: JsAny {
//    val publicKey: Uint8Array
//    val privateKey: Uint8Array
//}

external object Keypair: JsAny {
    val publicKey: Uint8Array
    val privateKey: Uint8Array
}
//
external object CryptoKxServerSessionKeysResult: JsAny {
    val sharedRx: Uint8Array
    val sharedTx: Uint8Array
}

external object CryptoSecretboxDetachedResult: JsAny {
    val cipher: Uint8Array
    val mac: Uint8Array
}

external object CryptoSecretstreamXchacha20poly1305InitPushResult: JsAny {
    val state: Uint8Array
    val header: Uint8Array
}

external object CryptoSecretstreamXchacha20poly1305PullResult: JsAny {
    val message: Uint8Array
    val tag: Byte
}
