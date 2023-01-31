package com.android.busapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.trip_item_row.view.*

class TripItemAdapter(val context: Context, val items: ArrayList<TripModelClass>) : RecyclerView.Adapter<TripItemAdapter.ViewHolder>()
{
    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_item_row, parent, false))
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = items[position]

        holder.tvId.text = (item.tid).toString()
        holder.tvBid.text = (item.bid).toString()
        holder.tvFrom.text = item.start
        holder.tvTo.text = item.dest
        holder.tvDate.text = item.date
        holder.tvTime.text = item.time

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

            if (context is ShowTrip) //context is fn from which it is called
            {
                context.updateRecordDialog(item)
            }
        }
        holder.ivDelete.setOnClickListener{

            if (context is ShowTrip)
            {
                context.deleteRecordAlertDialog(item)
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int
    {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        // Holds the TextView that will add each item to
        val llMain = view.llMain
        val tvId = view.tvId
        val tvBid = view.tvBid
        val tvFrom = view.tvFrom
        val tvTo = view.tvTo
        val tvDate = view.tvDate
        val tvTime = view.tvTime
        val ivEdit = view.ivEdit
        val ivDelete = view.ivDelete
    }
}