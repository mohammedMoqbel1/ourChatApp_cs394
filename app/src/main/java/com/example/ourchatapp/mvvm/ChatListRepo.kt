package com.example.ourchatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.RecentChats
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatListRepo {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllChatList() : LiveData<List<RecentChats>> {

        val mainChatList = MutableLiveData<List<RecentChats>>()

        firestore.collection("Conversations${Utils.getUiLoggedIn()}").orderBy("time",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val chatList = mutableListOf<RecentChats>()

                snapshot?.forEach{ documnent ->

                    val recentModel = documnent.toObject(RecentChats::class.java)

                    if(recentModel.sender.equals(Utils.getUiLoggedIn())){

                        recentModel.let {

                            chatList.add(it)
                        }
                    }

                    mainChatList.value = chatList


                }


            }

        return mainChatList

    }




}