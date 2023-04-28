package com.example.myapplication


import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Address
//import android.location.Geocoder
//import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//  Убираем название проекта и делаем экран полностью черным
        supportActionBar?.hide()
//  Настраиваем время заставки
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,ChangeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }


}