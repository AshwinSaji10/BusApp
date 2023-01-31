package com.android.busapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddTrip : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trip)

        val etId: EditText =findViewById(R.id.etId)
        val etBid: EditText =findViewById(R.id.etBid)
        val etStart: EditText =findViewById(R.id.etStart)
        val etDest: EditText =findViewById(R.id.etDest)
        val etDate: EditText =findViewById(R.id.etDate)
        val etTime: EditText =findViewById(R.id.etTime)

        val btnAdd: Button =findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener{
            addTrip(etId,etBid,etStart,etDest,etDate,etTime)
        }
    }
    private fun addTrip(etId:EditText,etBid:EditText,etStart:EditText, etDest:EditText,etDate:EditText,etTime:EditText)
    {
        val id=etId.text.toString()
        val bid=etBid.text.toString()
        val start=etStart.text.toString()
        val dest=etDest.text.toString()
        val date=etDate.text.toString()
        val time=etTime.text.toString()

        val dbhandler = DatabaseHandler(this)
        if (id.isNotEmpty() && bid.isNotEmpty() && start.isNotEmpty() && dest.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty())
        {
            val status = dbhandler.addTrip(TripModelClass(id.toInt(),bid.toInt(),start,dest,date,time))
            if (status > -1)
            {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_SHORT).show()
                //clear fields for new record entry
                etId.text.clear()
                etBid.text.clear()
                etStart.text.clear()
                etDest.text.clear()
                etDate.text.clear()
                etTime.text.clear()
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