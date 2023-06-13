package com.el3asas.utils.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.el3asas.utils.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import timber.log.Timber
import kotlin.math.log2

@BindingAdapter(
    "app:map_locations",
    "app:map_placeHolder",
    requireAll = false
)
fun addStaticMap(
    view: ImageView, drawable: Drawable? = null, locations: List<LatLng>
) {
    val latLngBounds = LatLngBounds.builder()
    locations.forEach {
        latLngBounds.include(it)
    }
    val mLocations = latLngBounds.build()
    val center = mLocations.center.getStaticAddress()
    val distance = SphericalUtil.computeSignedArea(locations)
    val zoom = log2(360 * view.width / distance) - 8

    val markers = locations.getMarkers()

    val url = buildStaticMapsUrl(view, center, markers, zoom.toString())
    Timber.d("map url $url")
    bindImgCenterInside(view, url, drawable)
}

private fun buildStaticMapsUrl(view: View, center: String, markers: String, zoom: String): String {
    val stringBuilder = StringBuilder()

    stringBuilder.apply {
        append("https://maps.googleapis.com/maps/api/staticmap?")
        append("size=${view.width}x${view.height}&")
        append(markers)
        append("center=$center&")
        append("zoom=$zoom&")
        append("key=" + view.resources.getString(R.string.maps_key))
    }
    return stringBuilder.toString()
}

@BindingAdapter(
    "app:map_single_location_lat",
    "app:map_single_location_lng",
    "app:map_single_location_zoom",
    "app:map_placeHolder",
    requireAll = false
)
fun addMapSingleLocation(
    view: ImageView, lat: Double, lng: Double, zoom: Float? = 14f, placeHolder: Drawable?
) {
    val marker = LatLng(lat, lng).getMarker()
    val url = buildStaticMapsUrl(view, marker, marker, zoom.toString())
    bindImgCenterInside(
        view, url, placeHolder
    )
}

fun LatLng.getStaticAddress() = this.latitude.toString() + "," + this.longitude.toString()

fun List<LatLng>.getMarkers(colorsAndLabels: List<String>? = null): String {
    var markers = ""
    this.forEachIndexed { index, latLng ->
        markers += "markers=" + (colorsAndLabels?.get(index)
            ?: "color:red%7Clabel:${Math.random()}%7C") + latLng.getStaticAddress() + "&"
    }
    return markers
}

fun LatLng.getMarker(colorsAndLabels: String? = null): String {
    return "markers=" + (colorsAndLabels
        ?: "color:red%7Clabel:${Math.random()}%7C") + this.getStaticAddress() + "&"
}
