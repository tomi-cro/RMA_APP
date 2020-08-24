package com.example.rma_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rma_app.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var email: String
    lateinit var password: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener{
            registration()
        }

        alreadyHaveAnAccount.setOnClickListener {
            goToLogin()
        }
    }

    private fun registration() {
        email = emailReg.text.toString()
        password = passwordReg.text.toString()
        firstName = firstNameReg.text.toString()
        lastName = lastNameReg.text.toString()
        address = addressReg.text.toString()

        if(email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty()) {
            Toast.makeText(this,"Please, enter your email, password, first name, last name and place!", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
            .addOnCompleteListener(OnCompleteListener<SignInMethodQueryResult> { task ->
                val isNewUser = task.result!!.signInMethods!!.isEmpty()

                if (isNewUser) {
                    registration(email, password)
                } else {
                    Log.e("TAG", "Is Old User!")
                }
            })
    }

    private fun registration(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                if(!it.isSuccessful) {
                    return@addOnCompleteListener
                }

                Log.d("RegistratonActivity", "Successfully created user with uid: ${it.result!!.user!!.uid}")

                saveUserToFirebase()
            }
            .addOnFailureListener{
                Log.d("RegistratonActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this,"Failed to create user: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun saveUserToFirebase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")

        val user = User(uid, firstNameReg.text.toString(), lastNameReg.text.toString(), addressReg.text.toString())

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegistrationActivity", "User is saved to Firebase.")

                val intent = Intent(this, HomePageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK) //pritiskom na back button vodi van iz aplikacije, a ne na RegistrationActivity
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("RegistrationActivity", "Failed to save user to Firebase: ${it.message}")
            }
    }
}
class FirebaseUser(val uid: String, val username: String, val firstName: String, val lastName: String)