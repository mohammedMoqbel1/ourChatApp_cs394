package com.example.ourchatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ourchatapp.R
import com.example.ourchatapp.Utils
import com.example.ourchatapp.model.messages

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    private var listOfMessage= listOf<messages>()
    private val LEFT =0
    private val RIGHT =1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == RIGHT) {

            val view = inflater.inflate(R.layout.chatitemright, parent, false)
            MessageHolder(view)
        } else {
            val view = inflater.inflate(R.layout.chatitemright, parent, false)
            MessageHolder(view)
        }


    }

    override fun getItemCount() = listOfMessage.size


    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message = listOfMessage[position]

        holder.messageText.visibility = View.VISIBLE
        holder.timeOfSent.visibility = View.VISIBLE

        holder.messageText.setText (message.messages)
        holder.timeOfSent.text = message.time?.substring(0, 5) ?: ""
     }



    override fun getItemViewType(position: Int) =
        if (listOfMessage[position].sender == Utils.getUiLoggedIn())RIGHT else LEFT

    fun setList(newList: List<messages>) {

        this.listOfMessage = newList

    }




    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {
        val messageText: TextView = itemView.findViewById(R.id.show_message)
        val timeOfSent: TextView = itemView.findViewById(R.id.timeView)
    }
}
