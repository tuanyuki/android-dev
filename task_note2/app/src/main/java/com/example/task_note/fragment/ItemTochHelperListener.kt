package com.example.task_note.fragment

import androidx.recyclerview.widget.RecyclerView

interface ItemTochHelperListener {
    fun onItemDismiss(viewHolder:RecyclerView.ViewHolder,position:Int)
}