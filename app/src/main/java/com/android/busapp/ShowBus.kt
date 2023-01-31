package com.android.busapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bus_update.*

class ShowBus : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_bus)

        setupListofDataIntoRecyclerView()
    }
    private fun getItemsList(): ArrayList<BusModelClass>
    {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val busList: ArrayList<BusModelClass> = databaseHandler.viewBus()

        return busList
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
            val itemAdapter = BusItemAdapter(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
            rvItemsList.adapter = itemAdapter
        }
        else//table is empty
        {
            rvItemsList.visibility = View.GONE
            //tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
    fun updateRecordDialog(bus: BusModelClass)
    {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.bus_update)

        //updateDialog.etUpdateId.setText((bus.id).toString())
        updateDialog.etUpdateLicense.setText(bus.license)
        updateDialog.etUpdateModel.setText(bus.model)
        updateDialog.etUpdateSeats.setText((bus.seats).toString())//important!! error without toString()

        updateDialog.tvUpdate.setOnClickListener{

            //val id = updateDialog.etUpdateId.text.toString()
            val license = updateDialog.etUpdateLicense.text.toString()
            val model = updateDialog.etUpdateModel.text.toString()
            val seats = updateDialog.etUpdateSeats.text.toString()

            val databaseHandler= DatabaseHandler(this)

            if (license.isNotEmpty() && model.isNotEmpty() && seats.isNotEmpty())
            {
                val status = databaseHandler.updateBus(BusModelClass(bus.id, license, model,seats.toInt()))
                if (status > -1)
                {
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_SHORT).show()

                    setupListofDataIntoRecyclerView()

                    updateDialog.dismiss() // Dialog will be dismissed
                }
                else
                {
                    Toast.makeText(applicationContext, "Database Error!", Toast.LENGTH_SHORT).show()
                    //updateDialog.dismiss()
                }
            }
            else
            {
                Toast.makeText(applicationContext, "Fields cannot be blank",Toast.LENGTH_SHORT).show()
            }
        }
        updateDialog.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        updateDialog.show()
    }
    fun deleteRecordAlertDialog(bus: BusModelClass)
    {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete this Bus?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            //creating the instance of DatabaseHandler class
            val databaseHandler= DatabaseHandler(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteBus(BusModelClass(bus.id, "", "",0,))
            if (status > -1)
            {
                Toast.makeText(applicationContext, "Record deleted successfully.", Toast.LENGTH_LONG).show()
                setupListofDataIntoRecyclerView()
            }
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}
