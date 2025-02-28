package com.example.wannahelp.profileEditScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wannahelp.databinding.ActivityProfileEditScreenBinding

class ProfileEditScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileEditScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileEditLayout.btnChangePhoto.setOnClickListener {
            showChangePhotoDialog()
        }
    }

    private fun showChangePhotoDialog() {
        val dialog = ChangePhotoDialogFragment.newInstance()
        dialog.show(supportFragmentManager, null)
    }
}