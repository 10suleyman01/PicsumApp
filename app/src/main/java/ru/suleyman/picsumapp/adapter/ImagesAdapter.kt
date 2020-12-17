package ru.suleyman.picsumapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.suleyman.picsumapp.R
import ru.suleyman.picsumapp.model.ImageModel

class ImagesAdapter : RecyclerView.Adapter<ImageHolder>() {

    var listener: OnImageClickListener? = null

    var imagesList: MutableList<ImageModel> = mutableListOf()

    fun loadImages(newImagesList: MutableList<ImageModel>) {
        imagesList.clear()
        imagesList.addAll(newImagesList)
        notifyDataSetChanged()
    }

    fun listener(listener: OnImageClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bindImage(imagesList[position])
        holder.imageView.setOnClickListener {
            listener?.onClicked(imagesList[position], holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = imagesList.size

    interface OnImageClickListener {
        fun onClicked(imageModel: ImageModel, position: Int)
    }
}