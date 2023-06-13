package com.el3asas.utils.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import coil.EventListener
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pl.droidsonroids.gif.GifDrawable
import timber.log.Timber

@BindingAdapter(
    "app:bindImgCenterCrop",
    "app:placeHolder",
    "app:loadingGifRes",
    "app:onSuccessLoadingImage",
    "app:imgScale",
    "app:allowHardware",
    requireAll = false
)
fun bindImgCenterCrop(
    v: ImageView,
    url: String,
    drawable: Drawable?,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    scale: Scale? = Scale.FILL,
    allowHardware: Boolean? = true,
) {
    try {
        v.scaleType = ImageView.ScaleType.CENTER_CROP
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess, scale, allowHardware)
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
}

@BindingAdapter(
    "app:bindImgFitCenter",
    "app:placeHolder",
    "app:loadingGifRes",
    "app:onSuccessLoadingImage",
    "app:imgScale",
    "app:allowHardware",
    requireAll = false
)
fun bindImgFitCenter(
    v: ImageView,
    url: String,
    drawable: Drawable?,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    scale: Scale? = Scale.FILL,
    allowHardware: Boolean? = true,
) {
    try {
        v.scaleType = ImageView.ScaleType.FIT_CENTER
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess, scale, allowHardware)
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
}

@BindingAdapter(
    "app:bindImgCenterInside",
    "app:placeHolder",
    "app:loadingGifRes",
    "app:onSuccessLoadingImage",
    "app:imgScale",
    "app:allowHardware",
    requireAll = false
)
fun bindImgCenterInside(
    v: ImageView,
    url: String,
    drawable: Drawable? = null,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    scale: Scale? = Scale.FILL,
    allowHardware: Boolean? = true,
) {
    try {
        v.scaleType = ImageView.ScaleType.CENTER_INSIDE
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
    try {
        bindImgWithPlaceHolder(v, url, drawable, loadingGifRes, onSuccess, scale, allowHardware)
    } catch (e: Exception) {
        Timber.d("******* error ${e.message}")
    }
}

@BindingAdapter(
    "app:bindImg",
    "app:placeHolder",
    "app:loadingGifRes",
    "app:onSuccessLoadingImage",
    "app:imgScale",
    "app:allowHardware",
    requireAll = false
)
fun bindImgWithPlaceHolder(
    imageView: ImageView,
    url: String,
    drawable: Drawable? = null,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    scale: Scale? = Scale.FILL,
    allowHardware: Boolean? = true,
) {
    bindWithCoil(
        imageView, url, drawable, loadingGifRes, onSuccess, scale, allowHardware
    )
}

private fun bindWithCoil(
    imageView: ImageView,
    url: String,
    drawable: Drawable? = null,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    scale: Scale? = Scale.FILL,
    allowHardware: Boolean? = true
) {
    val gif = loadingGifRes?.let { GifDrawable(imageView.context.resources, it) }
    imageView.load(
        url,
        imageLoader = ImageLoader.Builder(imageView.context).allowHardware(allowHardware ?: true)
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
            }).build(),
        builder = {
            scale(scale ?: Scale.FILL)
            crossfade(true)
            placeholder(gif ?: drawable)
            error(drawable)
        },
    )
}

@BindingAdapter(
    "app:glide_url",
    "app:glide_placeholder",
    "app:glide_loading_gif",
    "app:glide_onSuccessLoadingImage",
    "app:glide_scale",
    "app:glide_skipCache",
    requireAll = false
)
private fun bindWithGlide(
    imageView: ImageView,
    url: String,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes loadingGifRes: Int? = null,
    onSuccess: ((Drawable) -> Unit)? = null,
    glideScale: Boolean? = null,
    skipCache: Boolean? = false
) {
    val mDrawable = if (loadingGifRes != null) GifDrawable(
        imageView.context.resources, loadingGifRes
    ) else if (placeholder != null) ResourcesCompat.getDrawable(
        imageView.resources, placeholder, null
    ) else null
    Glide.with(imageView).load(url).placeholder(mDrawable).skipMemoryCache(skipCache ?: false)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (resource != null && onSuccess != null) {
                    onSuccess(resource)
                }
                return false
            }
        }).submit()
}