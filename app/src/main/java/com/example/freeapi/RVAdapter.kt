package com.example.freeapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

//import kotlinx.android.synthetic.main.single_item.view.*

class RVAdapter(private val names: List<Channel>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row
                ,parent
                ,false
            )
        )

    }
    


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val name =names [position]


        holder.itemView.apply{
            textView.text="${name.title}\n"
        }

    }


    override fun getItemCount() =    names.size


}