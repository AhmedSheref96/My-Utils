package com.el3asas.utils.utils

import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.el3asas.utils.utils.navigate

fun Fragment.isGpsOpen(openLocationDialogDirection: NavDirections, onGpsLogin: () -> Unit) {
    val manager = getSystemService(requireContext(), LocationManager::class.java)
    if (manager?.isProviderEnabled(LocationManager.GPS_PROVIDER)?.not() == true) {
        navigate(findNavController(this), openLocationDialogDirection)
    } else {
        onGpsLogin()
    }
}