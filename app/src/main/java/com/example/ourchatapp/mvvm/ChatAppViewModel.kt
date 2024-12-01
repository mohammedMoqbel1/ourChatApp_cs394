package com.example.ourchatapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ourchatapp.model.Users
import com.google.firebase.firestore.FirebaseFirestore

class ChatAppViewModel: ViewModel() {

    val message = MutableLiveData<String>()
    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


    val usersRepo = UsersRepo()
    val messageRepo = MessageRepo()
    var token: String? = null
    val chatlistRepo = ChatListRepo()
}