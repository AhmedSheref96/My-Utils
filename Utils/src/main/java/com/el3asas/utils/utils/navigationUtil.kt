package com.el3asas.utils.utils


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun navigate(
    fragment: Fragment,
    id: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    extras: Navigator.Extras?
) {
    try {
        fragment.findNavController().navigate(id, args, navOptions, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}


fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun navigate(
    view: View,
    id: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    extras: Navigator.Extras?
) {
    try {
        Navigation.findNavController(view).navigate(id, args, navOptions, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun navigate(
    navController: NavController,
    action: NavDirections,
    extras: Navigator.Extras
) {
    try {
        navController.navigate(action, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun navigate(
    navController: NavController,
    action: NavDirections
) {
    try {
        navController.navigate(action)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun navigate(
    navController: NavController,
    id: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    extras: Navigator.Extras?
) {
    try {
        navController.navigate(id, args, navOptions, extras)
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(navController: NavController, destinationId: Int?) {
    try {
        if (destinationId != null)
            navController.popBackStack(destinationId, false)
        else
            navController.popBackStack()


    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(view: View, destinationId: Int?) {
    try {
        if (destinationId != null)
            Navigation.findNavController(view).popBackStack(destinationId, false)
        else
            Navigation.findNavController(view).popBackStack()


    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

fun clearNavigateStack(fragment: Fragment) {
    try {
        NavHostFragment.findNavController(fragment).navigateUp()
    } catch (t: Throwable) {
        Timber.e("Multiple navigation attempts handled. $t")
    }
}

