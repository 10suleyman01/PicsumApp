package ru.suleyman.picsumapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.suleyman.picsumapp.R
import ru.suleyman.picsumapp.adapter.ImagesAdapter
import ru.suleyman.picsumapp.model.ImageModel
import ru.suleyman.picsumapp.ui.image.ImageActivity

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val IMAGE_URL = "imageUrl"
    }

    private lateinit var rvImageList: RecyclerView
    private lateinit var rvAdapter: ImagesAdapter

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1);

        rvImageList = findViewById(R.id.rvImageList)
        rvImageList.layoutManager = GridLayoutManager(this, 3)

        rvAdapter = ImagesAdapter()
        rvAdapter.listener(object : ImagesAdapter.OnImageClickListener {
            override fun onClicked(imageModel: ImageModel, position: Int) {
                val intent = Intent(this@MainActivity, ImageActivity::class.java)
                intent.putExtra(IMAGE_URL, imageModel.download_url)
                startActivity(intent)
            }
        })
        rvImageList.adapter = rvAdapter

        presenter = MainPresenter(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.loadImages()
        } else {
            Toast.makeText(this@MainActivity, "Not granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun loadImages(imagesList: MutableList<ImageModel>) {
        rvAdapter.loadImages(imagesList)
    }
}