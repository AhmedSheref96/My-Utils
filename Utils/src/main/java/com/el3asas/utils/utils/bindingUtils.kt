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
import coil.request.ImageRequest
import coil.request.SuccessResult
import pl.droidsonroids.gif.GifDrawable

@BindingAdapter(
    "app:bindImgCenterCrop", "app:placeHolder", "app:loadingGifRes",
    "app:onSuccessLoadingImage", requireAll = false
)
fun bindImgCenterCrop(
    v: ImageView, url: String, drawable: Drawable,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null
) {
    try {
        v.scaleType = ImageView.ScaleType.CENTER_CROP
    } catch (e: Exception) {
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess)
    } catch (e: Exception) {
    }
}

@BindingAdapter(
    "app:bindImgFitCenter", "app:placeHolder", "app:loadingGifRes",
    "app:onSuccessLoadingImage", requireAll = false
)
fun bindImgFitCenter(
    v: ImageView, url: String, drawable: Drawable,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null
) {
    try {
        v.scaleType = ImageView.ScaleType.FIT_CENTER
    } catch (e: Exception) {
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess)
    } catch (e: Exception) {
    }
}

@BindingAdapter(
    "app:bindImgCenterInside", "app:placeHolder", "app:loadingGifRes",
    "app:onSuccessLoadingImage", requireAll = false
)
fun bindImgCenterInside(
    v: ImageView, url: String, drawable: Drawable,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null
) {
    try {
        v.scaleType = ImageView.ScaleType.CENTER_INSIDE
    } catch (e: Exception) {
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess)
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
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null
) {
    val gif = loadingGifRes?.let { GifDrawable(imageView.context.resources, it) }
    imageView.load(
        url,
        imageLoader = ImageLoader.Builder(imageView.context)
            .allowHardware(false)
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
                        onSuccess(result.drawable)
                    }
                }
            })
            .build(),
        builder = {
            crossfade(true)
            placeholder(gif ?: drawable)
            error(drawable)
        },
    )

}
