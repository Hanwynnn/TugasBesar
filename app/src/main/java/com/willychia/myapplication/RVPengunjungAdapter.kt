package com.willychia.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.willychia.myapplication.entity.Pengunjung

class RVPengunjungAdapter (private val data: Array<Pengunjung>) : RecyclerView.Adapter<RVPengunjungAdapter.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_pengunjung, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvName.text = currentItem.name
        holder.tvDetails.text = "${ currentItem.email } - ${currentItem.noTelp}"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.tv_nama)
        val tvDetails : TextView = itemView.findViewById(R.id.tv_details)
    }
}