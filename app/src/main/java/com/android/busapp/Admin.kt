package com.android.busapp

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Admin : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val btnAddBus:Button=findViewById(R.id.btnAddBus)
        val btnAddTrip:Button=findViewById(R.id.btnAddTrip)
        val btnShowBus:Button=findViewById(R.id.btnShowBus)
        val btnShowTrip:Button=findViewById(R.id.btnShowTrip)

        val btnShowTrans:Button=findViewById(R.id.btnShowTrans)
        val btnShowUser:Button=findViewById(R.id.btnShowUser)

        //val new:Button=findViewById(R.id.btnnew)

        btnAddBus.setOnClickListener {
            val intent= Intent (this,AddBus::class.java)
            startActivity(intent)
        }
        btnAddTrip.setOnClickListener {
            val intent= Intent (this,AddTrip::class.java)
            startActivity(intent)
        }
        btnShowTrip.setOnClickListener {
            val intent= Intent (this,ShowTrip::class.java)
            startActivity(intent)
        }
        btnShowBus.setOnClickListener {
            val intent= Intent (this,ShowBus::class.java)
            startActivity(intent)
        }
        btnShowTrans.setOnClickListener {
            val intent= Intent (this,ShowTrans::class.java)
            startActivity(intent)
        }
        btnShowUser.setOnClickListener {
            val intent= Intent (this,ShowUsers::class.java)
            startActivity(intent)
        }
        /*
        new.setOnClickListener {
            val intent= Intent (this,new_function::class.java)
            startActivity(intent)
        }
         */
    }
}