package com.mohammad.hiltworkmanager.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mohammad.hiltworkmanager.R
import java.util.*


fun showNotification(context: Context) {
    val channelId = context.getString(R.string.notification_channel_id)
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("textTitle")
        .setContentText("textContent")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(context).also {
            builder.setChannelId(it.id)
        }
    }
    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        val random = Random().nextInt()
        notify(random, builder.build())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(context: Context): NotificationChannel {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    val name = context.getString(R.string.channel_name)
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(
        context.getString(R.string.notification_channel_id), name, importance
    )
    // Register the channel with the system
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
    return channel

}

fun createNotification(context: Context): Notification {
    val channelId = context.getString(R.string.notification_channel_id)
    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Increase number")
        .setContentText("This is expedited work request")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOngoing(true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(context).also {
            builder.setChannelId(it.id)
        }
    }
    return builder.build()
}