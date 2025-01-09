package com.example.ourchatapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ourchatapp.MyApplication
import com.example.ourchatapp.SharedPrefs
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.Users
import com.example.ourchatapp.model.Messages
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class  ChatAppViewModel: ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    val message = MutableLiveData<String>()
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

        firestore.collection("Users").document(Utils.getUiLoggedIn()).addSnapshotListener{ value, error->

            if(value!!.exists()&& value!=null){
                val users = value.toObject(Users::class.java)
                name.value = users?.username!!
                imageUrl.value = users.imageUrl!!


                val mySharedPrefs = SharedPrefs(context)

                mySharedPrefs.setValue("username", users.username)
            }
        }


    }

    // receive messages

    fun getMessages(friendId: String): LiveData<List<Messages>> {

        return messageRepo.getMessages(friendId)
    }


        //    Send Message
    fun sendMessage(sender :String, receiver: String, friendName  : String, friendImage:String )= viewModelScope.launch(Dispatchers.IO){

        val context= MyApplication.instance.applicationContext

        val hashMap = hashMapOf<String,Any>(
            "sender" to sender, "receiver" to receiver, "message" to message.value!!, "time" to Utils.getTime()
        )

        val uniqueId = listOf(sender,receiver).sorted()
        uniqueId.joinToString(separator = "")

        val friendNameSplit = friendName.split("\\s".toRegex())[0]
        val mySharedPrefs = SharedPrefs(context)
        mySharedPrefs.setValue("friendId",receiver)
        mySharedPrefs.setValue("chatRoomId",uniqueId.toString())
        mySharedPrefs.setValue("friendName",friendNameSplit)
        mySharedPrefs.setValue("friendImage",friendImage)

//        sending message

        firestore.collection("Messages").document(uniqueId.toString()).collection("chats").document(Utils.getTime()).set(hashMap).addOnCompleteListener{task->


//            for recent chats list
            val hashMapForRecent = hashMapOf<String,Any>(
                "friendId" to receiver,
                "time" to Utils.getTime(),
                "sender" to Utils.getUiLoggedIn(),
                "message" to message.value!!,
                "friendImage" to friendImage,
                "name" to friendName,
                "person" to "you"
            )



            firestore.collection("Conversation${Utils.getUiLoggedIn()}").document(receiver).set(hashMapForRecent)
            firestore.collection("Conversation${receiver}").document(Utils.getUiLoggedIn()).update("message",message.value!!,"time",Utils.getTime(),"person",name.value!!)


            if (task.isSuccessful){

                message.value = ""


            }



        }




    }




}



