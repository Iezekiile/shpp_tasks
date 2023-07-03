package com.example.shpp_task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shpp_task1.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val fullName = parseEmailToName(intent.getStringExtra("email").toString())
        val fullNameText = resources.getString(R.string.full_name, fullName.first, fullName.second)
        binding.fullName!!.text = fullNameText
        setContentView(view)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun parseEmailToName(email: String): Pair<String, String> {
        val parts = email.split("@").firstOrNull()?.split(".")
        val firstName = parts?.getOrNull(0)
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            ?: ""
        val lastName = if ((parts?.size ?: 0) > 1) {
            parts?.getOrNull(1)
                ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                ?: ""
        } else {
            ""
        }
        return Pair(firstName, lastName)
    }
}