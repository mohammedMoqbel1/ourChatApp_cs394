@file:Suppress("DEPRECATION")

package com.example.ourchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ourchatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var signUpAuth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var name : String
    private lateinit var signUpPd : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        firestore = FirebaseFirestore.getInstance()
        signUpPd = ProgressDialog(this)
        signUpAuth = FirebaseAuth.getInstance()

        signUpBinding.signUpTextToSignIn.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //TODO : In the sign up I have an exception for the passwor it wont work if the passsword is less  than 6 digits


        signUpBinding.signUpBtn.setOnClickListener {
            email = signUpBinding.signUpEmail.text.toString()
            password = signUpBinding.signUpPassword.text.toString()
            name = signUpBinding.signUpEtName.text.toString()


            if (signUpBinding.signUpEtName.text.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else if (signUpBinding.signUpEmail.text.isEmpty()) {
                Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show()
            } else if (signUpBinding.signUpPassword.text.isEmpty()) {
                Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
            } else if (signUpBinding.signUpPassword.text.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                signUpUser(name, email, password)
            }
        }
    }

    private fun signUpUser(name: String, email: String, password: String) {

        signUpPd.show()
        signUpPd.setMessage("Signing Up...")

        signUpAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->

            if (it.isSuccessful) {

                val user = signUpAuth.currentUser

                val hashMap = hashMapOf(
                    "userid" to user!!.uid,
                    "username" to name,
                    "useremail" to email,
                    "status" to "default",
                    "imageUrl" to "https://www.pngarts.com/files/6/User-Avatar-in-Suit-PNG.png"
                )

                firestore.collection("Users").document(user.uid).set(hashMap)

                signUpPd.dismiss()

                startActivity(Intent(this, SignInActivity::class.java))
            }

        }
    }

}




