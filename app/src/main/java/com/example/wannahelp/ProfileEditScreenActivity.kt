package com.example.wannahelp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wannahelp.databinding.ActivityProfileEditScreenBinding

class ProfileEditScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileEditScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

// todo добавить меню


    }

    fun startDialog() {
        val dialog = ChangePhotoDialogFragment.newInstance()
        dialog.show(supportFragmentManager, "viu viu")
    }
}