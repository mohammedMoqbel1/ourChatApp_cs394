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


        signInBinding.signInTextToSignUp.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}