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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.ourchatapp.R
import com.example.ourchatapp.Utils
import com.example.ourchatapp.databinding.FragmentChatBinding
import com.example.ourchatapp.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView


class ChatFragment : Fragment() {


    private lateinit var args: ChatFragmentArgs
    private lateinit var chatBinding: FragmentChatBinding
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var chattoolabar: Toolbar

    private lateinit var circleImageView: CircleImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvStatus: TextView
    private lateinit var backbtn:ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return chatBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args= ChatFragmentArgs.fromBundle(requireArguments())

        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)


        chattoolabar =view.findViewById(R.id.toolBarChat)
        circleImageView=chattoolabar.findViewById(R.id.chatImageViewUser)
        tvStatus = view.findViewById(R.id.chatUserStatus)
        tvUserName = view.findViewById(R.id.chatUserName)
        backbtn=chattoolabar.findViewById(R.id.chatBackBtn)


        backbtn.setOnClickListener{
            view.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }


        Glide.with(requireContext()).load(args.users.imageUrl).into(circleImageView)
        tvStatus.setText(args.users.status)
        tvUserName.setText(args.users.username)



        chatBinding.viewModel = chatAppViewModel
        chatBinding.lifecycleOwner = viewLifecycleOwner

        chatBinding.sendBtn.setOnClickListener{
            chatAppViewModel.sendMessage(Utils.getUiLoggedIn(),args.users.userid!!,args.users.username!!,args.users.imageUrl!!)
        }




    }

}