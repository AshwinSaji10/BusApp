package com.android.busapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_item_row.view.*

class TransactionItemAdapter(val context: Context, val items: ArrayList<TransactionModelClass>) : RecyclerView.Adapter<TransactionItemAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_item_row,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = items[position]

        holder.tvTransId.text = (item.transid).toString()
        holder.tvUid.text = item.uid.toString()
        holder.tvName.text = item.name
        holder.tvTid.text = item.tid.toString()
        holder.tvSeats.text = item.seats.toString()
        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0)
        {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
        }
        else
        {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
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
        val tvTransId = view.tvTransId
        val tvUid = view.tvUid
        val tvName=view.tvName
        val tvTid = view.tvTid
        val tvSeats= view.tvSeats

    }
}