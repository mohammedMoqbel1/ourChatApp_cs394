@file:Suppress("DEPRECATION")

package com.example.ourchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.ourchatapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

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

            if (email.isEmpty() || password.isEmpty()){

                signInBinding.loginetemail.error = "Email or Password is empty"
                signInBinding.loginetpassword.requestFocus()


            }else{

                progressDialogSignIn.setTitle("Signing In")
                progressDialogSignIn.setMessage("Please wait while we sign you in")
                progressDialogSignIn.setCanceledOnTouchOutside(false)
                progressDialogSignIn.show()

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                    if (task.isSuccessful){

                        progressDialogSignIn.dismiss()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{

                        progressDialogSignIn.dismiss()
                        signInBinding.loginetemail.error = "Email or Password is incorrect"
                        signInBinding.loginetpassword.requestFocus()
                    }
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