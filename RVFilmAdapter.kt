package com.willychia.TugasBesar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willychia.TugasBesar.Room.RoomFilm.NoteFilm
import kotlinx.android.synthetic.main.activity_edit_film.view.*
import kotlinx.android.synthetic.main.rv_item_film.view.*

class RVFilmAdapter (private val data: ArrayList<NoteFilm>, private val listener: OnAdapterListener) : RecyclerView.Adapter<RVFilmAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_film, parent, false)
        )
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteFilm = data[position]
        holder.view.tv_namaFilm.text = noteFilm.judul
        holder.view.tv_details2.text = "${noteFilm.genre} - ${noteFilm.rating}"
        holder.view.tv_namaFilm.setOnClickListener{
            listener.onClick(noteFilm)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(noteFilm)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(noteFilm)
        }
    }
    override fun getItemCount() = data.size
    inner class NoteViewHolder( val view: View) : RecyclerView.ViewHolder(view)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<NoteFilm>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(note: NoteFilm)
        fun onUpdate(note: NoteFilm)
        fun onDelete(note: NoteFilm)
    }

//    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        val currentItem = data[position]
//        holder.tvNamaFilm.text = currentItem.judul
//        holder.tvDetails2.text = "${currentItem.genre} - ${currentItem.rating}"
//    }

//    override fun getItemCount(): Int {
//        return data.size
//    }

//    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvNamaFilm: TextView = itemView.findViewById(R.id.tv_namaFilm)
//        val tvDetails2: TextView = itemView.findViewById(R.id.tv_details2)
//    }
}