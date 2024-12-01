package com.example.ourchatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourchatapp.MyApplication
import com.example.ourchatapp.SharedPrefs
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class  ChatAppViewModel: ViewModel() {

    val message = MutableLiveData<String>()
    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


    val usersRepo = UsersRepo()
    val messageRepo = MessageRepo()
    var token: String? = null
    val chatlistRepo = ChatListRepo()



    init {
        getCurrentUser()
    }


    fun getUsers(): LiveData<List<Users>> {
        return usersRepo.getUsers()
    }

    fun getCurrentUser()= viewModelScope.launch(Dispatchers.IO){
        val context= MyApplication.instance.applicationContext

        firestore.collection("Users").document(Utils.getUiLoggedIn()).addSnapshotListener{value,error->

            if(value!!.exists()&& value!=null){
                val users = value.toObject(Users::class.java)
                name.value = users?.username!!
                imageUrl.value = users.imageUrl!!


                val mySharedPrefs = SharedPrefs(context)

                mySharedPrefs.setValue("username", users.username)
            }
        }


    }


}