package com.android.busapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddBus : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bus)

        val etId: EditText =findViewById(R.id.etId)
        val etLicense: EditText =findViewById(R.id.etLicense)
        val etModel: EditText =findViewById(R.id.etModel)
        val etSeats: EditText =findViewById(R.id.etSeats)

        val btnAdd: Button =findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener{
            addTrip(etId,etLicense,etModel,etSeats)
        }
    }
    private fun addTrip(etId:EditText,etLicense:EditText,etModel:EditText, etSeats:EditText)
    {
        val id=etId.text.toString()
        val license=etLicense.text.toString()
        val model=etModel.text.toString()
        val seats=etSeats.text.toString()

        val dbhandler = DatabaseHandler(this)
        if (id.isNotEmpty() && license.isNotEmpty() && model.isNotEmpty() && seats.isNotEmpty())
        {
            val status = dbhandler.addBus(BusModelClass(id.toInt(),license,model,seats.toInt()))
            if (status > -1)
            {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_SHORT).show()
                //clear fields for new record entry
                etId.text.clear()
                etLicense.text.clear()
                etModel.text.clear()
                etSeats.text.clear()
            }
            else
            {
                Toast.makeText(applicationContext, "Database Error!", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(applicationContext, "Fields cannot be blank!", Toast.LENGTH_SHORT).show()
        }
    }
}