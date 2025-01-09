package com.example.ourchatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.Messages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessageRepo {

    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(friendId: String): LiveData<List<Messages>> {
        val uniqueId = listOf(Utils.getUiLoggedIn(), friendId).sorted()
        uniqueId.joinToString("")

        val messages = MutableLiveData<List<Messages>>()

        firestore.collection("Messages").document(uniqueId.toString()).collection("chats")
            .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    return@addSnapshotListener
                }

                val messagesList = mutableListOf<Messages>()
                if ( !snapshot!!.isEmpty) {
                    snapshot.documents.forEach { document ->
                        val messageModel = document.toObject(Messages::class.java)
                        if (messageModel!!.sender.equals(Utils.getUiLoggedIn()) && messageModel.receiver.equals(friendId) ||
                            messageModel!!.sender.equals(friendId) && messageModel.receiver.equals(Utils.getUiLoggedIn())) {
                            messageModel.let {
                                messagesList.add(it)
                            }
                        }
                    }
                    messages.value = messagesList
                }
            }

        return messages
    }
}