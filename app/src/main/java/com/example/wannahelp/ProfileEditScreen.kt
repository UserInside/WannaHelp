package com.example.wannahelp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wannahelp.databinding.ActivityProfileEditScreenBinding

class ProfileEditScreen : AppCompatActivity() {
    lateinit var binding: ActivityProfileEditScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}