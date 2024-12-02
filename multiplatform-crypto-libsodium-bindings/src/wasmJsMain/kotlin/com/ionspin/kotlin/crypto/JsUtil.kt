package ext.libsodium.com.ionspin.kotlin.crypto

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

/**
 * Created by Ugljesa Jovanovic
 * ugljesa.jovanovic@ionspin.com
 * on 02-Aug-2020
 */
fun UByteArray.toByteArray() : ByteArray {
//    val uint8Result = ByteArray(toTypedArray())
    return toByteArray()
}


fun Uint8Array.toUByteArray() : UByteArray {
    if (length == null) {
        println("Error")
    }
    val result = UByteArray(length)
    for (i in 0 until length) {
        result[i] = get(i).toUByte()
    }

    return result
}
