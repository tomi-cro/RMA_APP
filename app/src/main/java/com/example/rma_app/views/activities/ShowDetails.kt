package com.example.rma_app.views.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.rma_app.R
import com.example.rma_app.model.Show
import com.example.rma_app.utils.Utility.setTextHTML
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetails : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        auth = FirebaseAuth.getInstance();

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }

        val show = intent.getSerializableExtra("show") as? Show
        val userId = auth.currentUser?.uid

        displayShowDetails(show)

        setRemindMeBtnState(show?.status, userId!!)
        showOnFavList(userId, show?.id!!, showDetailReminderBtn)

        showDetailReminderBtn.setOnClickListener{
            if(showDetailReminderBtn.text == "Remove from favourites"){
                removeFromFavourites(userId, show?.id!!)
            }
            else{
                addShowToDb(show!!, userId)
            }
        }
    }

    private fun displayShowDetails(show: Show?) {
        Picasso.get().load(show?.image?.medium).into(showDetailCover)
        showDetailTitle.text = setTextHTML("<b>Title: </b>" + show?.name)
        showDetailSummary.text = setTextHTML("<b>Summary: </b>" + show?.summary)
        showDetailPremiered.text = setTextHTML("<b>Premiered: </b>" + show?.premiered)
        showDetailStatus.text = setTextHTML("<b>Status: </b>" + show?.status)
    }

    private fun setRemindMeBtnState(showStatus: String?, userId: String) {
        if(showStatus != "Running"){
            showDetailReminderBtn.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigToHome()
        return true
    }

    fun showOnFavList(userId: String, showId: String, showDetailReminderBtn: TextView) {
        FirebaseDatabase.getInstance().reference
            .child("favourite_shows/$userId")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(i in dataSnapshot.children){
                        val id = i.child("id").getValue().toString()
                        if(id == showId)
                            showDetailReminderBtn.text = "Remove from favourites"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
    }

    private fun navigToHome() {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun removeFromFavourites(userId: String, showId: String) {
        FirebaseDatabase.getInstance().reference
            .child("favourite_shows/$userId")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(i in dataSnapshot.children){
                        val id = i.child("id").getValue().toString()
                        if(id == showId){
                            i.ref.removeValue()
                            showDetailReminderBtn.text = "Remind me!!"
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
    }

    fun addShowToDb(show: Show, userId: String){
        val database = FirebaseDatabase.getInstance().getReference("favourite_shows/$userId")
        database.child(show.id).setValue(show)
        showDetailReminderBtn.text = "Remove from favourites"
    }
}