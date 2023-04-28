package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

    }
    // Функции нажатия на кнопку с переходом на другую activity
    // Переход на страницу с GPS
    fun changeGPSButton(view: View){
        val intent = Intent(this,GPSActivity::class.java)
        startActivity(intent)
    }
    // Переход на работу с картой
    fun changeMiraculusButton(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}