package com.nta.chat_kotlin.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.nta.chat_kotlin.R
import com.nta.chat_kotlin.common.Messages

class adapterChat(val context: Context, val list:ArrayList<Messages>): RecyclerView.Adapter<adapterChat.viewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewModel {
        val view:View = LayoutInflater.from(context).inflate(R.layout.layout_adapter_chat,parent,false)
        return viewModel(view)
    }

    override fun onBindViewHolder(holder: viewModel, position: Int) {
        if(list[position].uid.toString().equals(holder.mAuth.uid.toString())){
            holder.txtSend.visibility = View.VISIBLE
            holder.txtReceived.visibility = View.GONE
            holder.txtSend.setText(list[position].messages)
        }else {
            holder.txtReceived.visibility = View.VISIBLE
            holder.txtSend.visibility = View.GONE
            holder.txtReceived.setText(list[position].messages)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class viewModel(itemview: View): RecyclerView.ViewHolder(itemview) {
        val txtSend:TextView = itemview.findViewById(R.id.idTVsend_Chat_ADP)
        val txtReceived:TextView = itemview.findViewById(R.id.idTVreceived_Chat_ADP)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    }
}