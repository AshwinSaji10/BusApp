package com.android.busapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bus_item_row.view.*

class BusItemAdapter(val context: Context, val items: ArrayList<BusModelClass>) : RecyclerView.Adapter<BusItemAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.bus_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = items[position]

        holder.tvId.text = (item.id).toString()
        holder.tvLicense.text = item.license
        holder.tvModel.text = item.model
        holder.tvSeats.text = (item.seats).toString()
        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0)
        {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
        }
        else
        {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        holder.ivEdit.setOnClickListener{

            if (context is ShowBus) //context is fn from which it is called
            {
                context.updateRecordDialog(item)
            }
        }
        holder.ivDelete.setOnClickListener{

            if (context is ShowBus)
            {
                context.deleteRecordAlertDialog(item)
            }
        }
    }

    override fun getItemCount(): Int
    {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        // Holds the TextView that will add each item to
        val llMain = view.llMain
        val tvId = view.tvId
        val tvLicense = view.tvLicense
        val tvModel = view.tvModel
        val tvSeats= view.tvSeats
        val ivEdit = view.ivEdit
        val ivDelete = view.ivDelete
    }

}