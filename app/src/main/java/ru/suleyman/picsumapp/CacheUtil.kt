package ru.suleyman.picsumapp

import android.graphics.Bitmap
import java.io.InputStream
import java.io.OutputStream
import java.lang.ref.SoftReference
import java.util.*
import kotlin.collections.HashMap


class CacheUtil {

    private val cache: MutableMap<String, SoftReference<Bitmap>> =
        Collections.synchronizedMap(HashMap<String, SoftReference<Bitmap>>())

    operator fun get(id: String): Bitmap? {
        if (!cache.containsKey(id)) return null
        val ref: SoftReference<Bitmap>? = cache[id]
        return ref?.get()
    }

    fun put(id: String, bitmap: Bitmap?) {
        cache[id] = SoftReference<Bitmap>(bitmap)
    }

    fun clear() {
        cache.clear()
    }

    fun copy(inputStream: InputStream, outputStream: OutputStream) {
        val buffer_size = 1024
        try {
            val bytes = ByteArray(buffer_size)
            while (true) {
                val count: Int = inputStream.read(bytes, 0, buffer_size)
                if (count == -1) break
                outputStream.write(bytes, 0, count)
            }
        } catch (ex: Exception) {
        }
    }
}