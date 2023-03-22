package com.el3asas.utils.utils

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

private val intent by lazy { Intent(Intent.ACTION_SEND) }

fun shareLink(
    v: View,
    mDomainUriPrefix: String,
    siteLink: String,
    myTitle: String?,
    myDescription: String?,
    myImageUrl: String?,
    iosPackage: String? = null,
) {

    Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
        link = Uri.parse(siteLink)
        domainUriPrefix = mDomainUriPrefix
        androidParameters { }
        iosPackage?.let { iosParameters(it) { } }
        socialMetaTagParameters {
            title = myTitle ?: ""
            description = myDescription ?: ""
            myImageUrl?.let {
                val imgParsedUrl = Uri.parse(it.replace(" ", "%20"))
                imageUrl = imgParsedUrl
            }
        }
    }.addOnCompleteListener {
        intent.apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_SUBJECT, it.result.shortLink.toString()
            )
            putExtra(
                Intent.EXTRA_TEXT, it.result.shortLink.toString()
            )
        }
        val mIntent = Intent.createChooser(intent, "اختر تطبيقا")
        ContextCompat.startActivity(v.context, mIntent, null)
    }
}

fun getDynamicLink(
    v: View,
    siteLink: String,
    mDomainUriPrefix: String,
    myTitle: String?,
    myDescription: String?,
    myImageUrl: String?,
    iosPackage: String? = null,
    onSuccess: ((Task<ShortDynamicLink>) -> Unit)? = null
) {

    Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
        link = Uri.parse(siteLink)
        domainUriPrefix = mDomainUriPrefix
        androidParameters { }
        iosPackage?.let { iosParameters(it) { } }
        socialMetaTagParameters {
            title = myTitle ?: ""
            description = myDescription ?: ""
            myImageUrl?.let {
                val imgParsedUrl = Uri.parse(it.replace(" ", "%20"))
                imageUrl = imgParsedUrl
            }
        }
    }.addOnCompleteListener {
        if (onSuccess != null) {
            onSuccess(it)
        }
    }
}

fun createNotificationDeepLink(
    siteLink: String,
    mDomainUriPrefix: String,
    iosPackage: String? = null,
    onSuccess: (intent: Intent) -> Unit
) {
    Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
        link = Uri.parse(siteLink)
        domainUriPrefix = mDomainUriPrefix
        androidParameters { }
        iosPackage?.let { iosParameters(it) { } }
        socialMetaTagParameters {}
    }.addOnCompleteListener {
        intent.action = Intent.ACTION_VIEW
        intent.apply {
            data = Uri.parse(it.result.shortLink.toString())
        }
        onSuccess(intent)
    }
}