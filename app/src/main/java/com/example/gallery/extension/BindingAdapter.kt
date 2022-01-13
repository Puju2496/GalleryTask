package com.example.gallery.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.gallery.R
import com.example.gallery.model.Photo

@BindingAdapter("imageUrl")
fun setImage(image: ImageView, photo: Photo) {
    Glide.with(image.context)
        .load(photo.url)
        .placeholder(R.drawable.placeholder_image)
        .centerInside()
        .override(photo.width, photo.height)
        .into(image)
}