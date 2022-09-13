package com.el3asas.utils.utils


import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
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

fun <T : Parcelable> NavController.handleResult(
    lifecycleOwner: LifecycleOwner,
    @IdRes currentDestinationId: Int,
    @IdRes childDestinationId: Int,
    handler: (T) -> Unit
) {
    // `getCurrentBackStackEntry` doesn't work in case of recovery from the process death when dialog is opened.
    val currentEntry = getBackStackEntry(currentDestinationId)
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            handleResultFromChild(childDestinationId, currentEntry, handler)
        }
    }
    currentEntry.lifecycle.addObserver(observer)
    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            currentEntry.lifecycle.removeObserver(observer)
        }
    })
}


fun <T : Parcelable> NavController.finishWithResult(result: T) {
    val currentDestinationId = currentDestination?.id
    if (currentDestinationId != null) {
        previousBackStackEntry?.savedStateHandle?.set(resultName(currentDestinationId), result)
    }
    popBackStack()
}

private fun resultName(resultSourceId: Int) = "result-$resultSourceId"

private fun <T : Parcelable> handleResultFromChild(
    @IdRes childDestinationId: Int,
    currentEntry: NavBackStackEntry,
    handler: (T) -> Unit
) {
    val expectedResultKey = resultName(childDestinationId)
    if (currentEntry.savedStateHandle.contains(expectedResultKey)) {
        val result = currentEntry.savedStateHandle.get<T>(expectedResultKey)
        handler(result!!)
        currentEntry.savedStateHandle.remove<T>(expectedResultKey)
    }
}