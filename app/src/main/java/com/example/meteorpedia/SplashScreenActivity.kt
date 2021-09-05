package com.example.meteorpedia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       Handler().postDelayed({
           val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
           startActivity(intent)
           finish()
       }, 1500)
    }
}