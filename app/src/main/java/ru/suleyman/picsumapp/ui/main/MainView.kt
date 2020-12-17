package ru.suleyman.picsumapp.ui.main

import ru.suleyman.picsumapp.model.ImageModel

interface MainView {

    fun loadImages(imagesList: MutableList<ImageModel>)

}