package ext.libsodium.com.ionspin.kotlin.crypto

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

/**
 * Created by Ugljesa Jovanovic
 * ugljesa.jovanovic@ionspin.com
 * on 02-Aug-2020
 */
fun UByteArray.toByteArray() : ByteArray {
    return toByteArray()
}

fun UByteArray.toUInt8Array() : Uint8Array {
    var jsArray = JsArray<JsNumber>()
    for (i in this.indices) {
        jsArray[i] = this[i].toInt().toJsNumber()
    }
    var uint8Result = Uint8Array(jsArray)
    return uint8Result
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
