package com.el3asas.utils.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

const val CENTER_CROP = 0
const val CENTER_FIT = 1
const val CENTER_INSIDE = 2

@BindingAdapter("app:bindImgCenterCrop", "app:placeHolder")
fun bindImgCenterCrop(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, CENTER_CROP)
    } catch (e: Exception) {
    }
}

@BindingAdapter("app:bindImgFitCenter", "app:placeHolder")
fun bindImgFitCenter(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, CENTER_FIT)
    } catch (e: Exception) {
    }
}

@BindingAdapter("app:bindImgCenterInside", "app:placeHolder")
fun bindImgCenterInside(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, CENTER_INSIDE)
    } catch (e: Exception) {
    }
}

fun bindImgWithPlaceHolder(
    v: ImageView,
    url: String,
    drawable: Drawable? = null,
    position: Int? = null,
    onSuccessLoading: ((Boolean) -> Unit)?=null
) {
    try {
        val glide = Glide.with(v.context)
            .load(url)
            .placeholder(drawable)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (onSuccessLoading != null) {
                        onSuccessLoading(false)
                    }
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (onSuccessLoading != null) {
                        onSuccessLoading(true)
                    }
                    return true
                }
            })
        when (position) {
            CENTER_CROP -> glide.centerCrop().into(v)
            CENTER_INSIDE -> glide.centerInside().into(v)
            CENTER_FIT -> glide.fitCenter().into(v)
        }
    } catch (e: Exception) {
    }
}