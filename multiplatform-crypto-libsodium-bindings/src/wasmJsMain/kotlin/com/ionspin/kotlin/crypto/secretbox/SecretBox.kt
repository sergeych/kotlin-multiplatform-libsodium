package com.ionspin.kotlin.crypto.secretbox

import com.ionspin.kotlin.crypto.getSodium
import ext.libsodium.com.ionspin.kotlin.crypto.toUByteArray
import ext.libsodium.com.ionspin.kotlin.crypto.toUInt8Array

actual object SecretBox {
    actual fun easy(message: UByteArray, nonce: UByteArray, key: UByteArray): UByteArray {
        return getSodium().crypto_secretbox_easy(
            message.toUInt8Array(),
            nonce.toUInt8Array(),
            key.toUInt8Array()
        ).toUByteArray()
    }

    actual fun openEasy(
        ciphertext: UByteArray,
        nonce: UByteArray,
        key: UByteArray
    ): UByteArray {
        try {
            val decryptionResult = getSodium().crypto_secretbox_open_easy(
                ciphertext.toUInt8Array(),
                nonce.toUInt8Array(),
                key.toUInt8Array()
            )
            return decryptionResult.toUByteArray()
        } catch (error: Throwable) {
            throw SecretBoxCorruptedOrTamperedDataExceptionOrInvalidKey()
        }
    }

    actual fun detached(
        message: UByteArray,
        nonce: UByteArray,
        key: UByteArray
    ): SecretBoxEncryptedDataAndTag {
        val result = getSodium().crypto_secretbox_detached(
            message.toUInt8Array(),
            nonce.toUInt8Array(),
            key.toUInt8Array()
        )
        return SecretBoxEncryptedDataAndTag(
            result.cipher.toUByteArray(),
            result.mac.toUByteArray()
        )
    }

    actual fun openDetached(
        ciphertext: UByteArray,
        tag: UByteArray,
        nonce: UByteArray,
        key: UByteArray
    ): UByteArray {
        try {
            val decryptionResult = getSodium().crypto_secretbox_open_detached(
                ciphertext.toUInt8Array(),
                tag.toUInt8Array(),
                nonce.toUInt8Array(),
                key.toUInt8Array()
            )
            return decryptionResult.toUByteArray()
        } catch (error: Throwable) {
            throw SecretBoxCorruptedOrTamperedDataExceptionOrInvalidKey()
        }

    }

    actual fun keygen(): UByteArray {
        return getSodium().crypto_secretbox_keygen().toUByteArray()
    }

}
