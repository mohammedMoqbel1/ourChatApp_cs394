package com.example.ourchatapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ChatAppViewModel: ViewModel() {

    val name = MutableLiveData<String>()
    val imageUrl =  MutableLiveData<String>()
    val message =  MutableLiveData<String>()
}