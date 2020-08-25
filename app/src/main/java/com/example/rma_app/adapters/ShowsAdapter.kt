package com.example.rma_app.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rma_app.R
import com.example.rma_app.model.Show
import kotlinx.android.synthetic.main.show_row.view.*

class ShowsAdapter (private val shows: List<Show>) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showName: TextView = itemView.showName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowsAdapter.ViewHolder, position: Int) {
        holder.showName.text = shows[position].name
    }
}