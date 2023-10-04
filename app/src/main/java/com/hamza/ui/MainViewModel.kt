package com.hamza.ui

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.di.MainNotificationCompatBuilder
import com.hamza.di.SecondNotificationCompatBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val notificationManager: NotificationManagerCompat,
    @MainNotificationCompatBuilder
    private val notificationBuilder: NotificationCompat.Builder,
    @SecondNotificationCompatBuilder
    private val notificationBuilder2: NotificationCompat.Builder
) : ViewModel() {


    @SuppressLint("MissingPermission")
    fun showSimpleNotification() {
        notificationManager.notify(1, notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    fun updateNotification() {
        notificationManager.notify(
            1, notificationBuilder
                .setContentTitle("TITLE 2")
                .setContentText("Hello World 2 ")
                .build()
        )
    }

    @SuppressLint("MissingPermission")
    fun cancelNotification() {
        notificationManager.cancel(1)
    }

    @SuppressLint("MissingPermission")
    fun showProgress() {
        val max = 10
        var progress = 0
        viewModelScope.launch {
            while (progress != max) {
                delay(1000)
                progress += 1
                notificationManager.notify(
                    3,
                    notificationBuilder2
                        .setContentTitle("Downloading")
                        .setContentText("${progress}/${max}")
                        .setProgress(max, progress, false).build()
                )

            }
            notificationManager.notify(
                3,
                notificationBuilder
                    .setContentTitle("Completed!")
                    .setContentText("")
                    .setContentIntent(null)
                    .clearActions()
                    .setProgress(0, 0, false).build()
            )
        }
    }
}