package com.example.shpp_task1

import android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shpp_task1.databinding.ActivityAuthBinding

class AuthActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var email: String
        var password: String

    }
    fun validatePassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,}$")
        return pattern.matches(password)
    }
}