package com.el3asas.utils.utils

import android.graphics.Color
import android.graphics.Paint.Cap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.size.Scale

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
    imageView: ImageView,
    url: String,
    drawable: Drawable? = null,
    position: Int? = null
) {
/*
 try {
        Glide.with(v.context)
            .asGif()
            .load(drawable)
            .fitCenter()
            .into(v)

        val glide = Glide.with(v.context)
            .load(url)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (onSuccessLoading != null) {
                        onSuccessLoading(false, null)
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (onSuccessLoading != null) {
                        onSuccessLoading(true, resource)
                    }
                    return false
                }

            })
        when (position) {
            CENTER_CROP -> glide.centerCrop().into(v)
            CENTER_INSIDE -> glide.centerInside().into(v)
            CENTER_FIT -> glide.fitCenter().into(v)
        }
    } catch (e: Exception) {
    }
    */

    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 60f
    circularProgressDrawable.strokeCap = Cap.ROUND
    circularProgressDrawable.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
    circularProgressDrawable.start()
    imageView.load(url,
        imageLoader = ImageLoader.Builder(imageView.context)
            .addLastModifiedToFileCacheKey(false)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build(),
        builder = {
            crossfade(true)
            placeholder(circularProgressDrawable)
            when (position) {
                CENTER_CROP -> scale(Scale.FILL)
                CENTER_INSIDE, CENTER_FIT -> scale(Scale.FIT)
            }
            error(drawable)
        })

}