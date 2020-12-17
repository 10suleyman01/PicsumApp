package ru.suleyman.picsumapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class ImageNetworkUtil(context: Context) {

    var cacheUtil = CacheUtil()
    var fileUtil: FileUtil
    var bitmap: Bitmap? = null
    var images: MutableMap<String, Bitmap> = Collections.synchronizedMap(WeakHashMap())

    init {
        fileUtil = FileUtil(context)
    }

    fun bindImage(imageUrl: String, imageView: ImageView) {

        GlobalScope.launch(IO) {
            if (bitmap == null) {
                bitmap = getBitmap(imageUrl)
                images[imageUrl] = bitmap!!
            }
            withContext(Main) {
               imageView.setImageBitmap(images[imageUrl])
            }
        }
    }

    private fun getBitmap(url: String): Bitmap? {

        val file: File = fileUtil.getFile(url)

        var bitmap: Bitmap? = null

        try {
            val imageUrl = URL(url)
            val connection = imageUrl.openConnection() as HttpURLConnection

            connection.connectTimeout = 20000
            connection.readTimeout = 20000
            connection.instanceFollowRedirects = true

            val inputSteam = connection.inputStream
            val output = FileOutputStream(file)
            cacheUtil.copy(inputSteam, output)
            output.close()
            bitmap = decodeFile(file)!!
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun decodeFile(f: File): Bitmap? {
        try {

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, options)

            val REQUIRED_SIZE = 200
            var width_tmp = options.outWidth
            var height_tmp = options.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

            val optionsScaleSize = BitmapFactory.Options()
            optionsScaleSize.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, optionsScaleSize)
        } catch (e: FileNotFoundException) {
        }
        return null
    }

}