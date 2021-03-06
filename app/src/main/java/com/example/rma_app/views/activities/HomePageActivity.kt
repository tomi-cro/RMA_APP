package com.example.rma_app.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.example.rma_app.R
import com.example.rma_app.views.fragments.FavouriteShowsFragment
import com.example.rma_app.views.fragments.ProfileFragment
import com.example.rma_app.views.fragments.ShowsFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        auth = FirebaseAuth.getInstance();
        val uuid = auth.currentUser?.uid

        navView.setCheckedItem(R.id.navAllShows);

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameLayout,
                ShowsFragment()
            )
            .commit()

        navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navSignOut -> {
                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                R.id.navProfile -> {
                    val fragment =
                        ProfileFragment()
                    val bundle = Bundle()
                    bundle.putString("uuid", uuid)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit()
                }
                R.id.navAllShows -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            ShowsFragment()
                        )
                        .commit()
                }
                R.id.navFavouriteShows -> {
                    val fragment =
                        FavouriteShowsFragment()
                    val bundle = Bundle()
                    bundle.putString("uuid", uuid)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}