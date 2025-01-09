package com.example.ourchatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourchatapp.R
import com.example.ourchatapp.model.RecentChats
import de.hdodenhof.circleimageview.CircleImageView

class RecentChatAdapter: RecyclerView.Adapter<RecentChatHolder>() {

    private var listofchats = listOf<RecentChats>()
    private var listener: OnRecentChatClicked? = null
    private var recentModel = RecentChats()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentchatlist, parent, false)

        return RecentChatHolder(view)

    }

    override fun getItemCount(): Int {

        return listofchats.size

    }

    override fun onBindViewHolder(holder: RecentChatHolder, position: Int) {

        val recentchatlist = listofchats[position]

        recentModel = recentchatlist

        holder.userName.setText(recentchatlist.name)

        val themessage = recentchatlist.message!!.split("").take(4).joinToString(" ")
        val makelastmessage = "${recentchatlist.person}: ${themessage} "

        holder.lastMessage.setText(makelastmessage)

        Glide.with(holder.itemView.context).load(recentchatlist.friendsimage).into(holder.imageView)

        holder.timeView.setText(recentchatlist.time!!.substring(0,5))

        holder.itemView.setOnClickListener {
            listener?.getOnRecentChatClicked(position, recentchatlist)
        }


    }

       fun setOnRecentChatClickedListener(listener: OnRecentChatClicked){
        this.listener = listener
     }


    fun setRecentChatList(list: List<RecentChats>){
        this.listofchats = list
    }

}

class RecentChatHolder(itemview : View) : RecyclerView.ViewHolder(itemview){

    val imageView: CircleImageView = itemView.findViewById(R.id.recentChatImageView)
    val userName: TextView = itemView.findViewById(R.id.recentChatTextName)
    val lastMessage: TextView = itemView.findViewById(R.id.recentChatTextLastMessage)
    val timeView: TextView = itemView.findViewById(R.id.recentChatTextTime)

}

interface OnRecentChatClicked{
    fun getOnRecentChatClicked(position: Int,recentchatlist: RecentChats)
}