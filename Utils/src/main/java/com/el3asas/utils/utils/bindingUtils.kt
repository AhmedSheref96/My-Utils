package com.el3asas.utils.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.EventListener
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.el3asas.utils.R
import pl.droidsonroids.gif.GifDrawable

const val CENTER_CROP = 0
const val CENTER_FIT = 1
const val CENTER_INSIDE = 2

@BindingAdapter("app:bindImgCenterCrop", "app:placeHolder")
fun bindImgCenterCrop(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, R.drawable.loading_gif)
    } catch (e: Exception) {
    }
}

@BindingAdapter("app:bindImgFitCenter", "app:placeHolder")
fun bindImgFitCenter(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, R.drawable.loading_gif)
    } catch (e: Exception) {
    }
}

@BindingAdapter("app:bindImgCenterInside", "app:placeHolder")
fun bindImgCenterInside(v: ImageView, url: String, drawable: Drawable) {
    try {
        bindImgWithPlaceHolder(v, url, drawable, R.drawable.loading_gif)
    } catch (e: Exception) {
    }
}

@BindingAdapter(
    "app:bindImg",
    "app:placeHolder",
    "app:loadingGifRes",
    "app:onSuccessLoadingImage",
    requireAll = false
)
fun bindImgWithPlaceHolder(
    imageView: ImageView,
    url: String,
    drawable: Drawable? = null,
    @DrawableRes loadingGifRes: Int,
    onSuccess: (() -> Unit)? = {}
) {
    val gif = GifDrawable(imageView.context.resources, loadingGifRes)
    imageView.load(
        url,
        imageLoader = ImageLoader.Builder(imageView.context)
            .diskCachePolicy(CachePolicy.DISABLED)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.eventListener(object : EventListener {
                override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                    super.onSuccess(request, result)
                    if (onSuccess != null) {
                        onSuccess()
                    }
                }
            })
            .build(),
        builder = {
            crossfade(true)
            placeholder(gif)
            error(drawable)
        },
    )

}
