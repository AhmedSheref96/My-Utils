package com.el3asas.utils.utils


import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.el3asas.utils.R
import java.util.*


private const val NOTIFICATION_ID = 0

@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendNotification(
    title: String,
    content: String,
    avatar: String?,
    contentIntent: Intent,
    applicationContext: Context
) {

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val eggImage = avatar?.let { getBitmapFromURL(it) }

    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.default_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(title)
        .setContentText(content)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(eggImage)
        .setPriority(NotificationCompat.PRIORITY_MAX)

    builder.setOngoing(true)

    notify(Random().nextInt(), builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}