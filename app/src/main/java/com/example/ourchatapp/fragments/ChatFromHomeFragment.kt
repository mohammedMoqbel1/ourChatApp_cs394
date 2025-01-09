package com.example.ourchatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ourchatapp.R
import com.example.ourchatapp.Utils
import com.example.ourchatapp.adapter.MessageAdapter
import com.example.ourchatapp.databinding.FragmentChatBinding
import com.example.ourchatapp.databinding.FragmentChatfromHomeBinding
import com.example.ourchatapp.model.Messages
import com.example.ourchatapp.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView


class ChatFromHomeFragment : Fragment() {


    private lateinit var args: ChatFromHomeFragmentArgs
    private lateinit var chatfromhomebinding: FragmentChatfromHomeBinding
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var chattoolabar: Toolbar

    private lateinit var circleImageView: CircleImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvStatus: TextView
    private lateinit var backbtn: ImageView
    private lateinit var messageAdapter: MessageAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatfromhomebinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatfrom_home, container, false)
        return chatfromhomebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args= ChatFromHomeFragmentArgs.fromBundle(requireArguments())

        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)


        chattoolabar =view.findViewById(R.id.toolBarChat)
        circleImageView=chattoolabar.findViewById(R.id.chatImageViewUser)
        tvStatus = view.findViewById(R.id.chatUserStatus)
        tvUserName = view.findViewById(R.id.chatUserName)
        backbtn=chattoolabar.findViewById(R.id.chatBackBtn)


        backbtn.setOnClickListener{
            view.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }


        Glide.with(requireContext()).load(args.recenetchats.friendsimage).into(circleImageView)
        tvStatus.setText(args.recenetchats.status)
        tvUserName.setText(args.recenetchats.name)



        chatfromhomebinding.viewModel = chatAppViewModel
        chatfromhomebinding.lifecycleOwner = viewLifecycleOwner

        chatfromhomebinding.sendBtn.setOnClickListener{
            chatAppViewModel.sendMessage(Utils.getUiLoggedIn(),args.recenetchats.friendid!!,args.recenetchats.name!!,args.recenetchats.friendsimage!!)
        }

        chatAppViewModel.getMessages(args.recenetchats.friendid!!).observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })




    }



    private fun initRecyclerView(it:List<Messages>){

        messageAdapter = MessageAdapter()

        val layoutManager = LinearLayoutManager(context)

        chatfromhomebinding.messagesRecyclerView.layoutManager = layoutManager
        layoutManager.stackFromEnd = true

        messageAdapter.setMessageList(it)
        messageAdapter.notifyDataSetChanged()
        chatfromhomebinding.messagesRecyclerView.adapter = messageAdapter
    }

    }




