package ru.suleyman.picsumapp.ui.main

import android.util.Log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.suleyman.picsumapp.PicsumRepository

class MainPresenter(private val view: MainView) {

    private val TAG = "MainPresenter"

    fun loadImages() {
        GlobalScope.launch(IO) {
            val imagesList = PicsumRepository.picsum().getPhotos(1, 100)
            Log.d(TAG, "loadImages: $imagesList")
            withContext(Main) {
                view.loadImages(imagesList)
            }
        }
    }

}