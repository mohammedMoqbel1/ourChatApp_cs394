package com.example.ourchatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.messages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessageRepo {

    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(friendid: String): LiveData<List<messages>> {
        val uniqueId = listOf(Utils.getUiLoggedIn(), friendid).sorted().joinToString("")

        val liveData = MutableLiveData<List<messages>>()

        firestore.collection("Messages").document(uniqueId).collection("chats")
            .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    return@addSnapshotListener
                }

                val messagesList = mutableListOf<messages>()
                if (!snapshot!!.isEmpty) {
                    snapshot.documents.forEach { document ->
                        val messageModel = document.toObject(messages::class.java)
                        if (messageModel!!.sender.equals(Utils.getUiLoggedIn()) && messageModel.receiver.equals(friendid) ||
                                    messageModel!!.sender.equals(friendid) && messageModel.receiver.equals(Utils.getUiLoggedIn())) {
                            messageModel.let {
                                messagesList.add(it!!)
                            }
                        }
                    }
                    liveData.value = messagesList
                }
            }

        return liveData
    }
}