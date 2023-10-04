package com.hamza.ui

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.hamza.notifications.R
import com.hamza.notifications.databinding.ActivityMainBinding
import com.hamza.utils.Const
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkSaveStateControl


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        vm = ViewModelProvider(this)[MainViewModel::class.java]
        actions()
    }

    private fun actions() {
        binding.apply {
            showNotification.setOnClickListener {
                vm.showSimpleNotification()
            }
            updateNotification.setOnClickListener {
                vm.updateNotification()
            }
            cancelNotification.setOnClickListener {
                vm.cancelNotification()
            }
            btnDetailsScreen.setOnClickListener {
                openDetailsScreen()
            }
            btnDownload.setOnClickListener {
                vm.showProgress()
            }

        }
    }


    private fun openDetailsScreen() {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(Const.DETAILS_SCREEN_KEY, "Coming From Main Screen")
        startActivity(intent)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}