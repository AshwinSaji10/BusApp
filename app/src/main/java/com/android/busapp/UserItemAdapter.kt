package com.android.busapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_row.view.*

class UserItemAdapter(val context: Context, val items: ArrayList<UserModelClass>) : RecyclerView.Adapter<UserItemAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_row,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = items[position]

        holder.tvUid.text = (item.uid).toString()
        holder.tvAadhaar.text = item.aadhaar
        holder.tvPhone.text = item.phone
        holder.tvUname.text = item.uname
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
        val tvUid = view.tvUid
        val tvAadhaar = view.tvAadhaar
        val tvPhone = view.tvPhone
        val tvUname= view.tvUname

    }

}