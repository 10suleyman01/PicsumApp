package ru.suleyman.picsumapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.suleyman.picsumapp.ImageNetworkUtil
import ru.suleyman.picsumapp.R
import ru.suleyman.picsumapp.model.ImageModel

class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bindImage(imageModel: ImageModel) {
        ImageNetworkUtil(itemView.context).bindImage(imageModel.download_url, imageView)
    }

}