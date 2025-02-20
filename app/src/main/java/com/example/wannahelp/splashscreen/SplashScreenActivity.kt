package com.example.wannahelp.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wannahelp.MainActivity
import com.example.wannahelp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    this@SplashScreenActivity,
                    MainActivity::class.java
                )
            )
        }, 3000)
    }
}
