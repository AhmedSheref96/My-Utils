package com.el3asas.utils.utils

import android.content.Context
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest

/**
 * pre load images from network .
 * */
fun preLoadImages(context: Context, imgList: List<String>, allowHardware: Boolean = true) {
    val imageLoader = context.imageLoader
    val requestBuilder = ImageRequest.Builder(context).memoryCachePolicy(CachePolicy.DISABLED)
        .allowHardware(allowHardware)
    imgList.forEach {
        val request = requestBuilder.data(it).build()
        imageLoader.enqueue(request)
    }
}