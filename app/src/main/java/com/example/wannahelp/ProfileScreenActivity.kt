package com.example.wannahelp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wannahelp.databinding.ActivityProfileScreenBinding

class ProfileScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.action_profile
    }
}
