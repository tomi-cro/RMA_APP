package com.example.rma_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rma_app.model.Show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        val show = intent.getSerializableExtra("show") as? Show
        Picasso.get().load(show?.image?.medium).into(showDetailCover)
        showDetailTitle.text = show?.name
        showDetailSummary.text = show?.summary
        showDetailPremiered.text = show?.premiered
        showDetailStatus.text = show?.status
    }
}