package com.example.chatkotlinv2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkotlinv2.data.User
import com.example.chatkotlinv2.databinding.ListFriendsBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class AdapterFriends(options: FirebaseRecyclerOptions<User>) : FirebaseRecyclerAdapter<User,AdapterFriends.Companion.UserHolder>(
    options
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(ListFriendsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int, model: User) {
    }
    companion object{
        class UserHolder(binding:ListFriendsBinding):RecyclerView.ViewHolder(binding.root)
    }
}