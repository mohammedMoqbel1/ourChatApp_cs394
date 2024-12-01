@file:Suppress("DEPRECATION")

package com.example.ourchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
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

        signUpBinding.signUpBtn.setOnClickListener{
            email = signUpBinding.signUpEmail.text.toString()
            password = signUpBinding.signUpPassword.text.toString()
            name = signUpBinding.signUpEtName.text.toString()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()){
                signUpBinding.signUpEmail.error = "Email or Password or Name is empty"
                signUpBinding.signUpPassword.requestFocus()
                signUpBinding.signUpEtName.requestFocus()
            } else {
                signUpUser(name, email, password)
            }
        }
    }
      private fun signUpUser(name: String, email : String, password : String){
        signUpPd.setMessage("Please wait...")
        signUpPd.show()

        signUpAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = hashMapOf(
                    "username" to name,
                    "useremail" to email,
                    "status" to "default",
                    "imageUrl" to "https://www.pngarts.com/files/6/User-Avatar-in-Suit-PNG.png"
                )

                firestore.collection("users").document(signUpAuth.currentUser!!.uid).set(user).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        signUpPd.dismiss()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        signUpPd.dismiss()
                        signUpBinding.signUpEmail.error = task.exception!!.message
                        signUpBinding.signUpPassword.requestFocus()
                    }
                }
            } else {
                signUpPd.dismiss()
                signUpBinding.signUpEmail.error = task.exception!!.message
                signUpBinding.signUpEmail.requestFocus()
            }
        }
    }
}