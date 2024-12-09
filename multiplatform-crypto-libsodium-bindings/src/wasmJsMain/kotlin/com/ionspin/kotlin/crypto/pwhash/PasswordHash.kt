package com.ionspin.kotlin.crypto.pwhash

import com.ionspin.kotlin.crypto.getSodium
import com.ionspin.kotlin.crypto.util.encodeToUByteArray
import ext.libsodium.com.ionspin.kotlin.crypto.toUByteArray
import ext.libsodium.com.ionspin.kotlin.crypto.toUInt8Array

actual object PasswordHash {
    /**
     * The crypto_pwhash() function derives an outlen bytes long key from a password passwd whose length is passwdlen
     * and a salt whose fixed length is crypto_pwhash_SALTBYTES bytes. passwdlen should be at least crypto_pwhash_
     * PASSWD_MIN and crypto_pwhash_PASSWD_MAX. outlen should be at least crypto_pwhash_BYTES_MIN = 16 (128 bits) and
     * at most crypto_pwhash_BYTES_MAX.
     *
     * See https://libsodium.gitbook.io/doc/password_hashing/default_phf for more details
     */
    actual fun pwhash(
        outputLength: Int,
        password: String,
        salt: UByteArray,
        opsLimit: ULong,
        memLimit: Int,
        algorithm: Int
    ): UByteArray {
        if (opsLimit > UInt.MAX_VALUE) {
            throw RuntimeException("Javascript doesnt support more than ${UInt.MAX_VALUE} for opslimit")
        }
        return getSodium().crypto_pwhash(
            outputLength.toLong(),
            password.encodeToUByteArray().toUInt8Array(),
            salt.toUInt8Array(),
            opsLimit.toLong(),
            memLimit.toLong(),
            algorithm.toLong()
        ).toUByteArray()
    }

    /**
     * The crypto_pwhash_str() function puts an ASCII encoded string into out, which includes:
     * the result of a memory-hard, CPU-intensive hash function applied to the   password passwd of length passwdlen
     * the automatically generated salt used for the previous computation
     * the other parameters required to verify the password, including the algorithm identifier, its version, opslimit and memlimit.
     * out must be large enough to hold crypto_pwhash_STRBYTES bytes, but the actual output string may be shorter.
     * The output string is zero-terminated, includes only ASCII characters and can be safely stored into SQL databases
     * and other data stores. No extra information has to be stored in order to verify the password.
     * The function returns 0 on success and -1 if it didn't complete successfully.
     */
    actual fun str(password: String, opslimit: ULong, memlimit: Int): String {
        if (opslimit > UInt.MAX_VALUE) {
            throw RuntimeException("Javascript doesnt support more than ${UInt.MAX_VALUE} for opslimit")
        }
        return getSodium().crypto_pwhash_str(
            password.encodeToUByteArray().toUInt8Array(),
            opslimit.toLong(),
            memlimit.toLong()
        )
    }

    /**
     * Check if a password verification string str matches the parameters opslimit and memlimit, and the current default algorithm.
     * The function returns 1 if the string appears to be correct, but doesn't match the given parameters. In that situation, applications may want to compute a new hash using the current parameters the next time the user logs in.
     * The function returns 0 if the parameters already match the given ones.
     * It returns -1 on error. If it happens, applications may want to compute a correct hash the next time the user logs in.
     */
    actual fun strNeedsRehash(
        passwordHash: String,
        opslimit: ULong,
        memlimit: Int
    ): Int {
        if (opslimit > UInt.MAX_VALUE) {
            throw RuntimeException("Javascript doesnt support more than ${UInt.MAX_VALUE} for opslimit")
        }
        return if (
            getSodium().crypto_pwhash_str_needs_rehash(
                passwordHash,
                opslimit.toLong(),
                memlimit.toLong()
            )
        ) {
            1
        } else {
            0
        }
    }

    /**
     * his function verifies that str is a valid password verification string (as generated by crypto_pwhash_str()) for passwd whose length is passwdlen.
     * str has to be zero-terminated.
     * It returns 0 if the verification succeeds, and -1 on error.
     */
    actual fun strVerify(passwordHash: String, password: String): Boolean {
        return getSodium().crypto_pwhash_str_verify(
            passwordHash,
            password.encodeToUByteArray().toUInt8Array()
        )
    }

}
