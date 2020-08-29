package com.example.rma_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rma_app.R
import com.example.rma_app.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val uuid = arguments?.getString("uuid")
        if (uuid != null) {
            showUserDetails(uuid)
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun showUserDetails(uuid: String){
        FirebaseDatabase.getInstance().reference
            .child("users/$uuid")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var user = dataSnapshot.value as Map<String, Any>
                    profileFullName.text = setTextHTML("<b>Full name: </b>" + user["name"] + " " + user["surname"])
                    profileAddress.text = setTextHTML("<b>Address: </b>" + user["address"])
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
    }

    fun setTextHTML(html: String): Spanned {
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
        return result
    }
}