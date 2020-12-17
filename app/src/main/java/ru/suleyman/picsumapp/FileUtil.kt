package ru.suleyman.picsumapp

import android.content.Context
import android.os.Environment
import java.io.File

class FileUtil(context: Context) {

    var cacheDir: File = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) File(
        Environment.getExternalStorageDirectory(), "cache"
    ) else context.cacheDir

    init {
        if (!cacheDir.exists()) cacheDir.mkdirs()
    }

    fun getFile(url: String): File {
        val filename = url.hashCode().toString()
        return File(cacheDir, filename)
    }

    fun clear() {
        val files = cacheDir.listFiles() ?: return
        for (f in files) f.delete()
    }

}