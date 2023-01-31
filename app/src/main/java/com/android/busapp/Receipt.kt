package com.android.busapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import kotlinx.android.synthetic.main.activity_receipt.*

class Receipt : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val tvTripId: TextView =findViewById(R.id.tvTripId)
        val tvUid: TextView =findViewById(R.id.tvAadhaar)
        val tvUname: TextView =findViewById(R.id.tvUname)
        val tvModel: TextView =findViewById(R.id.tvModel)
        val tvStart: TextView =findViewById(R.id.tvStart)
        val tvDest: TextView =findViewById(R.id.tvDest)
        val tvDate: TextView =findViewById(R.id.tvDate)
        val tvTime: TextView =findViewById(R.id.tvTime)
        val tvSeats: TextView =findViewById(R.id.tvSeats)

        tvTripId.setText(intent.getStringExtra("trip_id"))
        tvAadhaar.setText(intent.getStringExtra("aadhaar"))
        tvUname.setText(intent.getStringExtra("uname"))
        tvModel.setText(intent.getStringExtra("model"))
        tvStart.setText(intent.getStringExtra("start"))
        tvDest.setText(intent.getStringExtra("dest"))
        tvDate.setText(intent.getStringExtra("date"))
        tvTime.setText(intent.getStringExtra("time"))
        tvSeats.setText(intent.getStringExtra("seats"))

    }
}