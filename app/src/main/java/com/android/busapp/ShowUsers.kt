package com.android.busapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowUsers : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_users)

        setupListofDataIntoRecyclerView()
    }
    private fun getItemsList(): ArrayList<UserModelClass>
    {
        val databaseHandler = DatabaseHandler(this)
        val userList: ArrayList<UserModelClass> = databaseHandler.viewUser()
        return userList
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
            val itemAdapter = UserItemAdapter(this, getItemsList())
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