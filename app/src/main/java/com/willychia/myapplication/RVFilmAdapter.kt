package com.willychia.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.willychia.myapplication.Room.RoomFilm.NoteFilm

class RVFilmAdapter (private val data: ArrayList<NoteFilm>) : RecyclerView.Adapter<RVFilmAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_film, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNamaFilm.text = currentItem.judul
        holder.tvDetails2.text = "${currentItem.genre} - ${currentItem.rating}"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaFilm: TextView = itemView.findViewById(R.id.tv_namaFilm)
        val tvDetails2: TextView = itemView.findViewById(R.id.tv_details2)
    }
}