package com.android.busapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowTrans : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_trans)

        setupListofDataIntoRecyclerView()
    }
    private fun getItemsList(): ArrayList<TransactionModelClass>
    {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val transList: ArrayList<TransactionModelClass> = databaseHandler.viewTrans()

        return transList
    }
    private fun setupListofDataIntoRecyclerView()
    {
        val rvItemsList: RecyclerView =findViewById(R.id.recyclerview)
        if (getItemsList().size > 0)
        {
            rvItemsList.visibility = View.VISIBLE
            //tvNoRecordsAvailable.visibility = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            rvItemsList.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = TransactionItemAdapter(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
            rvItemsList.adapter = itemAdapter
        }
        else//table is empty
        {
            rvItemsList.visibility = View.GONE
            //tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
}