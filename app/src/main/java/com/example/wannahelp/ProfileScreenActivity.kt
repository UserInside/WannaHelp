package com.example.wannahelp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.wannahelp.databinding.ActivityProfileScreenBinding

class ProfileScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.action_profile

        val toolbar = binding.appBar.toolbar
        setSupportActionBar(toolbar)

        onBackPressedDispatcher.addCallback(this) {
            finishAffinity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit_profile) {
            startActivity(Intent(this@ProfileScreenActivity, ProfileEditScreen::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
