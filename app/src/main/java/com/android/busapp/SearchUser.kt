package com.android.busapp

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*


class SearchUser : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)
        val etStartSearchUser: EditText =findViewById(R.id.etStartSearchUser)
        val etDestSearchUser: EditText =findViewById(R.id.etDestSearchUser)
        val tvDateDisplay: TextView =findViewById(R.id.tvDateDisplay)
        val btnPickDate:Button=findViewById(R.id.btnPickDate)
        val btnSearchUser:Button=findViewById(R.id.btnSearchUser)


        btnPickDate.setOnClickListener {
            clickdatepicker(tvDateDisplay)
        }
        //val selecteddate=tvDateDisplay.text.toString()

        btnSearchUser.setOnClickListener {
            SearchBus(etStartSearchUser,etDestSearchUser,tvDateDisplay)
        }
        

    }
    private fun clickdatepicker(tvDateDisplay:TextView)
    {
        val mycalendar = Calendar.getInstance()
        val year = mycalendar.get(Calendar.YEAR)
        val month = mycalendar.get(Calendar.MONTH)
        val day = mycalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            {
                    _,
                    selectedyear,
                    selectedmonth,
                    selecteddayOfMonth ->
                    val selecteddate = "$selectedyear-${selectedmonth+1}-$selecteddayOfMonth"
                    tvDateDisplay.text = selecteddate//set tvDateDisplay text as selected date
            }
            ,year
            ,month
            ,day
        )
        dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.datePicker.maxDate = System.currentTimeMillis()+86400000*20 // 86400000ms = 24hrs = 1 day
        dpd.show()
    }
    private fun SearchBus(etStartSearchUser: EditText,etDestSearchUser: EditText,tvDateDisplay:TextView)
    {
        val start=etStartSearchUser.text.toString()
        val dest=etDestSearchUser.text.toString()
        val date=tvDateDisplay.text.toString()

        //val resultsList:ArrayList<SearchResultsModelClass>

        //val dbhandler = DatabaseHandler(this)
        if (start.isNotEmpty() && dest.isNotEmpty() && date.isNotEmpty())
        {
            //setupListofDataIntoRecyclerView(start,dest,date)
            //resultsList = dbhandler.search(start,dest,date)
            val intent= Intent (this,SearchResults::class.java)
            intent.putExtra("start",start)//send values from this activity to another activity
            intent.putExtra("dest",dest)
            intent.putExtra("date",date)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(applicationContext, "Fields cannot be blank!", Toast.LENGTH_SHORT).show()
        }
    }

}