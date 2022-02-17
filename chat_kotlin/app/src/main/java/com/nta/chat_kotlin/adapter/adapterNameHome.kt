package com.nta.chat_kotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nta.chat_kotlin.ChatActivity
import com.nta.chat_kotlin.R
import com.nta.chat_kotlin.common.User

class adapterNameHome(val context: Context,val list:ArrayList<User>) :
    RecyclerView.Adapter<adapterNameHome.viewModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewModel {
        val view:View = LayoutInflater. from(context).inflate(R.layout.layout_adp_name,parent,false)
        return viewModel(view)
    }

    override fun onBindViewHolder(holder: viewModel, position: Int) {
        holder.txtName.setText(list[position].username)
        holder.cvMain.setOnClickListener({
            val intent:Intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("username",list[position].username)
            intent.putExtra("uid",list[position].uid)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class viewModel(itemview: View):RecyclerView.ViewHolder(itemview) {
        val txtName:TextView = itemview.findViewById(R.id.idTVname_ADP)
        val cvMain:CardView  = itemview.findViewById(R.id.idCV_ADP)
    }
}