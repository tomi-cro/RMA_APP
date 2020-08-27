package com.example.rma_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rma_app.adapters.ShowsAdapter
import com.example.rma_app.model.Show
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.show_recycler.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class HomePageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        auth = FirebaseAuth.getInstance();

        navView.setCheckedItem(R.id.navAllShows);

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.tvmaze.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllShows().enqueue(object : Callback<List<Show>>{
            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                Log.d("test", "onResponse " + response.body()!![1])
                showData(response.body()!!)
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                Log.d("test", "onFailure" + t.message)
            }
        })

        navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navSignOut -> {
                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }
    }

    private fun showData(shows: List<Show>){
        recViewShows.apply {
            layoutManager = GridLayoutManager(this@HomePageActivity, 2)
            adapter = ShowsAdapter(shows)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}