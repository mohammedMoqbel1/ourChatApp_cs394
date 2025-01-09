package com.example.ourchatapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ourchatapp.R
import com.example.ourchatapp.SignInActivity
import com.example.ourchatapp.adapter.OnItemClickListener
import com.example.ourchatapp.adapter.OnRecentChatClicked
import com.example.ourchatapp.adapter.RecentChatAdapter
import com.example.ourchatapp.adapter.UserAdapter
import com.example.ourchatapp.databinding.FragmentHomeBinding
import com.example.ourchatapp.model.RecentChats
import com.example.ourchatapp.model.Users
import com.example.ourchatapp.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment() , OnItemClickListener ,OnRecentChatClicked{

    lateinit var rvUsers: RecyclerView
    lateinit var useradapter: UserAdapter
    lateinit var userViewModel: ChatAppViewModel
    lateinit var homdebinding: FragmentHomeBinding
    lateinit var fbauth: FirebaseAuth
    lateinit var toolbar: Toolbar
    lateinit var circleImageView: CircleImageView
    lateinit var recentChatAdapter: RecentChatAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homdebinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        return homdebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbauth = FirebaseAuth.getInstance()

        toolbar = view.findViewById(R.id.toolbarMain)
        circleImageView = view.findViewById(R.id.tlImage)

        homdebinding.lifecycleOwner =viewLifecycleOwner

        userViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        useradapter = UserAdapter()
        rvUsers = view.findViewById(R.id.rvUsers)

        val layoutManagerUsers = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvUsers.layoutManager = layoutManagerUsers



        userViewModel.getUsers().observe(viewLifecycleOwner, Observer {
           useradapter.setList(it)

            rvUsers.adapter= useradapter
        })

        useradapter.setOnClickListener(this)


        homdebinding.logOut.setOnClickListener {
            fbauth.signOut()
            startActivity(Intent(requireContext(), SignInActivity::class.java))

        }

        userViewModel.imageUrl.observe(viewLifecycleOwner,Observer{
            Glide.with(requireContext()).load(it).into(circleImageView)
        })



        circleImageView.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }




        recentChatAdapter = RecentChatAdapter()

        userViewModel.getRecentChats().observe(viewLifecycleOwner,Observer{

            homdebinding.rvRecentChats.layoutManager = LinearLayoutManager(activity)
            recentChatAdapter.setRecentChatList(it)
            homdebinding.rvRecentChats.adapter = recentChatAdapter

        })

        recentChatAdapter.setOnRecentChatListener(this)




    }

    override fun onUserSelected(position: Int, users: Users) {

        val action = HomeFragmentDirections.actionHomeFragmentToChatFragment(users)
        view?.findNavController()?.navigate(action)

        Log.e("HOMEFRAGMENT","ClickedOn${users.username}")


    }

    override fun getOnRecentChatClicked(position: Int, recentChatList: RecentChats) {

        val action = HomeFragmentDirections.actionHomeFragmentToChatFromHomeFragment(recentChatList)
        view?.findNavController()?.navigate(action)
    }
}


