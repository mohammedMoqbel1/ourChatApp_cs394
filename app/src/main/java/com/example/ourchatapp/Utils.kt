package com.example.ourchatapp


import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class Utils {


    companion object {


        private val auth = FirebaseAuth.getInstance()
        private var userid: String = ""

        fun getUiLoggedIn(): String {

            if (auth.currentUser!=null){

                userid = auth.currentUser!!.uid
            }
            return userid

        }

        @SuppressLint("SimpleDateFormat")
        fun getTime(): String {


            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            val stringDate = formatter.format(date)


            return stringDate

        }




    }
}