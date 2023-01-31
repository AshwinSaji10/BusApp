package com.android.busapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_results_item_row.view.*

class SearchItemAdapter(val context: Context, val items: ArrayList<SearchResultsModelClass>) : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_results_item_row,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = items[position]

        holder.tvModel.text = item.model
        holder.tvStart.text = item.start
        holder.tvDest.text = item.dest
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
        holder.btnBook.setOnClickListener{

            if (context is SearchResults) //context is fn from which it is called
            {
                context.BookDialog(item)
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
        val tvModel = view.tvModel
        val tvStart = view.tvStart
        val tvDest = view.tvDest
        val tvDate= view.tvDate
        val tvTime= view.tvTime
        val btnBook=view.btnBook
        /*
        val ivEdit = view.ivEdit
        val ivDelete = view.ivDelete
        */
    }

}