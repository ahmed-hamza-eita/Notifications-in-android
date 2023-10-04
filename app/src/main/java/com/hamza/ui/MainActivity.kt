package com.hamza.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hamza.notifications.R
import com.hamza.notifications.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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
                showSimpleNotifications()
            }
            updateNotification.setOnClickListener {
                updateNotification()
            }
            cancelNotification.setOnClickListener {
                cancelNotification()
            }
        }
    }

    private fun showSimpleNotifications() {
        vm.showSimpleNotification()
    }

    private fun updateNotification() {
        vm.updateNotification()
    }
    private fun cancelNotification() {
        vm.cancelNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}