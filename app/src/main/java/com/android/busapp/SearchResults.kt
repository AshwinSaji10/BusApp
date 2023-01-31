package com.android.busapp

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_dialog_layout.*
import kotlinx.android.synthetic.main.search_results_item_row.*

class SearchResults : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results)

        /*
        val intent=intent
        val start=intent.getStringExtra("start")
        val dest=intent.getStringExtra("dest")
        val date=intent.getStringExtra("date")
        */
        setupListofDataIntoRecyclerView()
    }
    private fun getItemsList(start:String?,dest:String?,date:String?): ArrayList<SearchResultsModelClass>
    {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val resultsList: ArrayList<SearchResultsModelClass> = databaseHandler.search(start,dest,date)

        return resultsList
    }
    private fun setupListofDataIntoRecyclerView()
    {
        val intent=intent
        val start=intent.getStringExtra("start")//retrieve values from previous activity!
        val dest=intent.getStringExtra("dest")
        val date=intent.getStringExtra("date")

        val rvItemsList: RecyclerView =findViewById(R.id.recyclerview)
        if (getItemsList(start,dest,date).size > 0)//if there is at least 1 search result
        {
            rvItemsList.visibility = View.VISIBLE
            //tvNoRecordsAvailable.visibility = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            rvItemsList.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = SearchItemAdapter(this, getItemsList(start,dest,date))
            // adapter instance is set to the recyclerview to inflate the items.
            rvItemsList.adapter = itemAdapter
        }
        else//table is empty
        {
            rvItemsList.visibility = View.GONE
            Toast.makeText(applicationContext, "No available buses!", Toast.LENGTH_SHORT).show()
            //tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
    fun BookDialog(result: SearchResultsModelClass)
    {
        val bookDialog = Dialog(this, R.style.Theme_Dialog)
        bookDialog.setCancelable(true)
        bookDialog.setContentView(R.layout.book_dialog_layout)


        //when user selects book from dialog box
        bookDialog.tvBook.setOnClickListener{

            //val id = updateDialog.etUpdateId.text.toString()
            val aadhaar = bookDialog.etAadhaar.text.toString()
            val phone = bookDialog.etPhone.text.toString()
            val uname = bookDialog.etUname.text.toString()
            val seats = bookDialog.etSeats.text.toString()


            val databaseHandler= DatabaseHandler(this)

            if (phone.isNotEmpty() && aadhaar.isNotEmpty() && uname.isNotEmpty() && seats.isNotEmpty())//if all fields are not empty
            {
                if(seats.toInt() in 1..5)
                {
                    //check seats
                    val flag = databaseHandler.checkSeats(result.tid, seats.toInt())

                    if (flag == 1)//if enough seats are there
                    {
                        // insert into user table and insert into transaction table
                        val found_id=databaseHandler.addUser(UserModelClass(0,aadhaar,phone,uname))
                        val status_t = databaseHandler.addTrans(TransactionModelClass(0,found_id!!,result.tid,seats.toInt()))
                        if(status_t>-1&&found_id>-1)
                        {
                            //Toast.makeText(applicationContext, "Successfully inserted!", Toast.LENGTH_SHORT).show()
                            val intent= Intent (this,Receipt::class.java)
                            intent.putExtra("trip_id",(result.tid).toString())//send values from this activity to another activity
                            intent.putExtra("aadhaar",aadhaar)
                            //intent.putExtra("phone",p)
                            intent.putExtra("uname",uname)
                            intent.putExtra("model",result.model)
                            intent.putExtra("start",result.start)
                            intent.putExtra("dest",result.dest)
                            intent.putExtra("date",result.date)
                            intent.putExtra("time",result.time)
                            intent.putExtra("seats",seats)
                            startActivity(intent)
                            bookDialog.dismiss()
                        }
                        else if(found_id==-1)
                        {
                            Toast.makeText(applicationContext, "Data Mismatch!", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "Database Error", Toast.LENGTH_SHORT).show()
                        }
                        //Toast.makeText(applicationContext, "Enough seats are there", Toast.LENGTH_SHORT).show()
                        //setupListofDataIntoRecyclerView()

                        //updateDialog.dismiss() // Dialog will be dismissed
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Not enough seats!", Toast.LENGTH_SHORT).show()
                        //updateDialog.dismiss()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext, "Please enter valid number of seats!", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext, "Fields cannot be blank",Toast.LENGTH_SHORT).show()
            }
        }
        bookDialog.tvCancel.setOnClickListener{
            bookDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        bookDialog.show()
    }
}