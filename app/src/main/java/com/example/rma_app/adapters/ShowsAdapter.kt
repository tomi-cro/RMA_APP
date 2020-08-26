package com.example.rma_app.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rma_app.R
import com.example.rma_app.ShowDetails
import com.example.rma_app.model.Show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_row.view.*

class ShowsAdapter (private val shows: List<Show>) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showName: TextView = itemView.showName
        val showCover: ImageView = itemView.showCover
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_row, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener{
            val intent = Intent(parent.context, ShowDetails::class.java)
            intent.putExtra("show", shows[holder.adapterPosition])
            parent.context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowsAdapter.ViewHolder, position: Int) {
        holder.showName.text = shows[position].name
        Picasso.get().load(shows[position].image.medium).into(holder.showCover)
    }
}