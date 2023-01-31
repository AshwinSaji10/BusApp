package com.android.busapp

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnUser: Button =findViewById(R.id.btnUser)
        val btnAdmin: Button =findViewById(R.id.btnAdmin)

        btnUser.setOnClickListener{
            val intent= Intent (this,SearchUser::class.java)
            startActivity(intent)
        }
        btnAdmin.setOnClickListener{
            val intent= Intent (this,Admin::class.java)
            startActivity(intent)
        }
    }
}