package ru.suleyman.picsumapp.ui.image

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.suleyman.picsumapp.R
import ru.suleyman.picsumapp.ui.main.MainActivity
import java.net.URL
import java.net.URLConnection

class ImageActivity: AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var imageUrl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        imageUrl = findViewById(R.id.imageUrl)
        image = findViewById(R.id.imageView)

        val data = intent.extras

        if (data != null) {
            val url = data.getString(MainActivity.IMAGE_URL)

            imageUrl.text = url

            GlobalScope.launch(IO) {

                val openStream = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(openStream)

                withContext(Main) {
                    image.setImageBitmap(bitmap)
                }
            }
        }

    }
}