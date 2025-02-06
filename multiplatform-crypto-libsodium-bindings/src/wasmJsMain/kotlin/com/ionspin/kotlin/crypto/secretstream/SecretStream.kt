package com.ionspin.kotlin.crypto.secretstream

import com.ionspin.kotlin.crypto.getSodium
import ext.libsodium.com.ionspin.kotlin.crypto.SecretStreamStateType
import ext.libsodium.com.ionspin.kotlin.crypto.toUByteArray
import ext.libsodium.com.ionspin.kotlin.crypto.toUInt8Array


actual typealias SecretStreamState = SecretStreamStateType

actual object SecretStream {
    actual fun xChaCha20Poly1305InitPush(key: UByteArray): SecretStreamStateAndHeader {
        val state = getSodium().crypto_secretstream_xchacha20poly1305_init_push(key.toUInt8Array())
        return SecretStreamStateAndHeader(state.state, state.header.toUByteArray())
    }

    actual fun xChaCha20Poly1305Push(
        state: SecretStreamState,
        message: UByteArray,
        associatedData: UByteArray,
        tag: UByte
    ): UByteArray {
        return getSodium().crypto_secretstream_xchacha20poly1305_push(
            state, message.toUInt8Array(), associatedData.toUInt8Array(), tag.toByte()
        ).toUByteArray()
    }

    actual fun xChaCha20Poly1305InitPull(
        key: UByteArray,
        header: UByteArray
    ): SecretStreamStateAndHeader {
        val state = getSodium().crypto_secretstream_xchacha20poly1305_init_pull(header.toUInt8Array(), key.toUInt8Array())
        return SecretStreamStateAndHeader(state, header)
    }

    actual fun xChaCha20Poly1305Pull(
        state: SecretStreamState,
        ciphertext: UByteArray,
        associatedData: UByteArray
    ): DecryptedDataAndTag {
        // crypto_secretstream_xchacha20poly1305_pull JavaScript realization returns either 0
        // or DecryptedDataAndTagType object, therefore if 0 is returned an identifying exception is thrown
        try {
            val dataAndTag = getSodium().crypto_secretstream_xchacha20poly1305_pull(
                state, ciphertext.toUInt8Array(), associatedData.toUInt8Array()
            )
            return DecryptedDataAndTag(dataAndTag.message.toUByteArray(), dataAndTag.tag.toUByte())
        } catch (error: Throwable) {
            throw SecretStreamCorruptedOrTamperedDataException()
        }
    }

    actual fun xChaCha20Poly1305Keygen(): UByteArray {
        return getSodium().crypto_shorthash_keygen().toUByteArray()
    }

    actual fun xChaCha20Poly1305Rekey(state: SecretStreamState) {
        getSodium().crypto_secretstream_xchacha20poly1305_rekey(state)
    }

}
