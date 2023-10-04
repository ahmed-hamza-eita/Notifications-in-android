package com.hamza.ui

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val notificationManager: NotificationManagerCompat,
    private val notificationBuilder: NotificationCompat.Builder
) : ViewModel() {


    @SuppressLint("MissingPermission")
    fun showSimpleNotification(){
        notificationManager.notify(1,notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    fun updateNotification(){
        notificationManager.notify(1,notificationBuilder
            .setContentTitle("TITLE 2")
            .setContentText("Hello World 2 ")
            .build())
    }

    @SuppressLint("MissingPermission")
    fun cancelNotification(){
        notificationManager.cancel(1)
    }
}