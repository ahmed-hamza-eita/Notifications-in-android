package com.hamza.di

import android.annotation.SuppressLint
import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import com.hamza.utils.Const
import com.hamza.notifications.R
import com.hamza.receiver.MyReceiver
import com.hamza.ui.DetailsActivity
import com.hamza.ui.MainActivity
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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

//show message when clicked on button in Notification
        val actionIntent = Intent(context, MyReceiver::class.java).apply {
            putExtra("MESSAGE_KEY", "Clicked!!")
        }

        val actionPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            actionIntent,
            flag
        )

        // go to another screen

        val clickIntent = Intent(context, DetailsActivity::class.java)
        clickIntent.apply {
            putExtra(Const.DETAILS_SCREEN_KEY, "Coming from first screen")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }



        return NotificationCompat.Builder(context, Const.CHANNEL_ID)
            .setContentTitle("Welcome")
            .setContentText("hello world")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(0, "ACTION", actionPendingIntent)
            .setContentIntent(clickPendingIntent)
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