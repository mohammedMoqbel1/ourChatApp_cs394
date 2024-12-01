@file:Suppress("DEPRECATION")

package com.example.ourchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.ourchatapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SignInActivity : AppCompatActivity() {

    private lateinit var goToSignUpActivity: TextView
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var auth : FirebaseAuth
    private lateinit var progressDialogSignIn : ProgressDialog
    private lateinit var signInBinding: ActivitySignInBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        /// one user at a time , if logged in first

        //TODO: check if user is already logged in
        //TODO: When  user is already logged in,check if it takes you to main activity
        //TODO: when the code down below is commented out, it takes you to main activity when you are already logged in

        if (auth.currentUser != null){

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        progressDialogSignIn = ProgressDialog(this)


        signInBinding.signInTextToSignUp.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
        }

//yazid change **************************************

        signInBinding.loginButton.setOnClickListener {

            email = signInBinding.loginetemail.text.toString()
            password = signInBinding.loginetpassword.text.toString()

            if (signInBinding.loginetemail.text.isEmpty()) {
                Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show()
            } else if (signInBinding.loginetpassword.text.isEmpty()) {
                Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
            } else if (signInBinding.loginetpassword.text.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                signIn(password, email)
            }
        }
    }

    private fun signIn(password: String, email: String) {

        progressDialogSignIn.show()
        progressDialogSignIn.setMessage("Signing In...")


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                progressDialogSignIn.dismiss()
                startActivity(Intent(this, MainActivity::class.java))


            } else {

                progressDialogSignIn.dismiss()
                Toast.makeText(this, "Error: ${it.exception?.message}", Toast.LENGTH_SHORT).show()

            }

        }.addOnFailureListener{exception->
            when(exception){
                is FirebaseAuthInvalidUserException -> {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        progressDialogSignIn.dismiss()
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialogSignIn.dismiss()
    }
}