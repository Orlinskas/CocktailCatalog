package com.orlinskas.cocktail.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.orlinskas.cocktail.R

@BindingAdapter("setImageUrlWithGlide")
fun setImageUrlWithGlide(imageView: ImageView, url: String) = setImageUrl(imageView, url)

private fun setImageUrl(imageView: ImageView, url: String) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)

    Glide.with(imageView.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(imageView)
}
