package com.example.rma_app.views.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rma_app.R
import com.example.rma_app.adapters.ShowsAdapter
import com.example.rma_app.listeners.ReadDataListener
import com.example.rma_app.model.Image
import com.example.rma_app.model.Schedule
import com.example.rma_app.model.Show
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_shows.*

class FavouriteShowsFragment : Fragment() {
    lateinit var shows: ArrayList<Show>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_shows, container, false)

        val userId = arguments?.getString("uuid")
        getFavouriteShows(userId!!, object : ReadDataListener {
            override fun readData(list: ArrayList<Show>) {
                for(e in list) {
                    shows.add(e)
                }
                showData(shows)
            }
        })

        return root
    }

    fun getFavouriteShows(userId: String, readData: ReadDataListener){
        val ref = FirebaseDatabase.getInstance().getReference("favourite_shows/$userId")
        shows = ArrayList()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val shows: ArrayList<Show> = ArrayList()
                for(i in p0.children) {
                    val show = Show(
                        i.child("id").getValue().toString(),
                        i.child("name").getValue().toString(),
                        i.child("url").getValue().toString(),
                        i.child("language").getValue().toString(),
                        i.child("status").getValue().toString(),
                        i.child("premiered").getValue().toString(),
                        i.child("summary").getValue().toString(),
                        Image(i.child("image/medium").getValue().toString()),
                        Schedule(i.child("schedule/time").getValue().toString(), listOf(i.child("schedule/days").getValue().toString())))
                        shows.add(show)
                    }
                readData.readData(shows)
            }
        })
    }

    fun showData(shows: List<Show>){
        if(activity != null){
            recViewShows.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = ShowsAdapter(shows)
            }
        }
    }
}