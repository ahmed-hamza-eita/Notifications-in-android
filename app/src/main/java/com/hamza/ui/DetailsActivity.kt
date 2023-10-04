package com.hamza.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamza.notifications.databinding.ActivityDetailsBinding
import com.hamza.utils.Const
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkDsl


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var vm: MainViewModel
    private var message: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        actions()
    }

    private fun actions() {
        message = intent.getStringExtra(Const.DETAILS_SCREEN_KEY)
        binding.txtView.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}