package ext.libsodium.com.ionspin.kotlin.crypto

import org.khronos.webgl.Uint8Array


external object Sha256StateType: JsAny
external object Sha512StateType: JsAny

external object SignatureStateType: JsAny

external object AeadEncryptedType : JsAny {
    val ciphertext: Uint8Array
    var mac: Uint8Array
}

external object BoxEncryptedType : JsAny {
    val ciphertext: Uint8Array
    var mac: Uint8Array
}

external object KeyExchangeSessionKeyPairType: JsAny {
    val sharedRx: Uint8Array
    val sharedTx: Uint8Array
}

external object KeyExchangeKeyPairType: JsAny {
    val publicKey: Uint8Array
    val privateKey: Uint8Array
}

external object SecretBoxEncryptedType: JsAny {
    val cipher: Uint8Array
    val mac: Uint8Array
}

external object SecretStreamStateType: JsAny

external object SecretStreamStateAndHeaderType: JsAny {
    val state: SecretStreamStateType
    val header: Uint8Array
}

external object DecryptedDataAndTagType: JsAny {
    val message: Uint8Array
    val tag: Byte
}

external object GenericHashStateInternalType: JsAny

external object Blake2bInternalStateType: JsAny
