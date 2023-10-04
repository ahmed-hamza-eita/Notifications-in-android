package com.hamza.di

import android.annotation.SuppressLint
import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.hamza.utils.Const
import com.hamza.notifications.R
import com.hamza.receiver.MyReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @SuppressLint("ObsoleteSdkInt")
    @Singleton
    @Provides
    //How Notification look like
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }


        val intent = Intent(context, MyReceiver::class.java).apply {
            putExtra("MESSAGE_KEY", "Clicked!!")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flag
        )

        return NotificationCompat.Builder(context, Const.CHANNEL_ID)
            .setContentTitle("Welcome")
            .setContentText("hello world")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0,"ACTION", pendingIntent)
            // show notification in lock screen without details
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setPublicVersion(
                NotificationCompat.Builder(context, Const.CHANNEL_ID)
                    .setContentTitle("Hidden")
                    .setContentText("Unlock to see the message")
                    .build()
            )
    }


    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Const.CHANNEL_ID,
                "Main Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}