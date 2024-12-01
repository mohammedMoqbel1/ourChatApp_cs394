@file:Suppress("DEPRECATION")

package com.example.ourchatapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {


    private  lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val nagHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = nagHostFrag.navController

    }

    //todo : find the new one
   override fun onBackPressed() {

       if (supportFragmentManager.backStackEntryCount>0){
           super.onBackPressed()
       }else {
           if (navController.currentDestination?.id ==R.id.homeFragment){
               moveTaskToBack(true)
           }else{
               super.onBackPressed()

           }
       }

   }
}