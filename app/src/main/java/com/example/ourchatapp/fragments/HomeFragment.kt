package com.example.ourchatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourchatapp.R
import com.example.ourchatapp.adapter.UserAdapter
import com.example.ourchatapp.databinding.FragmentHomeBinding
import com.example.ourchatapp.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    lateinit var rvUsers: RecyclerView
    lateinit var useradapter: UserAdapter
    lateinit var userViewModel: ChatAppViewModel
    lateinit var homdebinding: FragmentHomeBinding

    lateinit var fbauth: FirebaseAuth


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

        userViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        useradapter = UserAdapter()
        rvUsers = view.findViewById(R.id.rvUsers)

        val layoutManagerUsers = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvUsers.layoutManager = layoutManagerUsers



        userViewModel.getUsers().observe(viewLifecycleOwner, Observer {
           useradapter.setList(it)
            rvUsers.adapter= useradapter
        })


        homdebinding.logOut.setOnClickListener {
            fbauth.signOut()



        }

    }
}