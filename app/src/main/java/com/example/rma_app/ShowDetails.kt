package com.example.rma_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import com.example.rma_app.model.Show
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetails : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        auth = FirebaseAuth.getInstance();

        val show = intent.getSerializableExtra("show") as? Show
        val userId = auth.currentUser?.uid
        Picasso.get().load(show?.image?.medium).into(showDetailCover)
        showDetailTitle.text = setTextHTML("<b>Title: </b>" + show?.name)
        showDetailSummary.text = setTextHTML("<b>Summary: </b>" + show?.summary)
        showDetailPremiered.text = setTextHTML("<b>Premiered: </b>" + show?.premiered)
        showDetailStatus.text = setTextHTML("<b>Status: </b>" + show?.status)

        showDetailReminderBtn.setOnClickListener{
            if (show != null) {
                if (userId != null) {
                    addShowToDb(show, userId)
                }
            }
        }
    }

    fun setTextHTML(html: String): Spanned {
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
        return result
    }

    fun addShowToDb(show: Show, userId: String){
        var database = FirebaseDatabase.getInstance().getReference("favourite_shows/$userId")

        database.child(show.id).setValue(show)
    }
}