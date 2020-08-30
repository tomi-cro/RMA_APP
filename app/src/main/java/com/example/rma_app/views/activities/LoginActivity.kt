package com.example.rma_app.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rma_app.R
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance();
        val user = auth.currentUser

        if(user != null){
            homePage()
        }

        btnLogin.setOnClickListener{
            login()
        }

        btnGoToRegister.setOnClickListener {
            goToRegistration()
        }

    }

    private fun login() {
        email = emailLogin.text.toString()
        password = passwordLogin.text.toString()

        if(email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this,"Please, enter your email or password!", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                if(!it.isSuccessful) {
                    return@addOnCompleteListener
                }

                Log.d("LoginActivity", "User is successfully logged in with uid: ${it.result?.user?.uid}")
                logIn()
            }
            .addOnFailureListener{
                Log.d("RegistratonActivity", "Failed to log in: ${it.message}")
                Toast.makeText(this,"Failed to log in: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun logIn() {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun goToRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun homePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
