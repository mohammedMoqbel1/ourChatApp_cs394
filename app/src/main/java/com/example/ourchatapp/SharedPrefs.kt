package com.example.ourchatapp

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("my_preferences",Context.MODE_PRIVATE)


    fun setValue(key:String, value:String){

        prefs.edit().putString(key,value).apply()
    }

    fun getValue(key:String):String?{

        return  prefs.getString(key,null)
    }

    fun setIntValue(key:String, value:Int){

        prefs.edit().putInt(key,value).apply()
    }

    fun getIntValue(key: String, i: Int): Int {

        return prefs.getInt(key, i.toInt())
    }
}