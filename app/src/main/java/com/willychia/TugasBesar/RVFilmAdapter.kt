package com.willychia.TugasBesar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.willychia.TugasBesar.FragmentFilm.Companion.LAUNCH_ADD_ACTIVITY
import com.willychia.TugasBesar.entity.Film
import java.util.*
import kotlin.collections.ArrayList

class RVFilmAdapter(private var filmList: List<Film>, context: Context):
    RecyclerView.Adapter<RVFilmAdapter.ViewHolder>(), Filterable {

    private var filteredFilmList: MutableList<Film>
    private val context: Context

    init {
        filteredFilmList = ArrayList(filmList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item_film, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredFilmList.size
    }

    fun setFilmList(filmList: Array<Film>){
        this.filmList = filmList.toList()
        filteredFilmList = filmList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = filteredFilmList[position]
        holder.tvJudul.text = film.judul
        holder.tvGenre.text = film.genre
        holder.tvRating.text = film.rating.toString()

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus mahasiswa ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus"){_,_ ->
                    if (context is FragmentFilm) film.id?.let { it1 ->
                        context.deleteFilm(
                            it1
                        )
                    }
                }
                .show()

        }

        holder.cvMahasiswa.setOnClickListener {
            val i = Intent(context, EditFilmActivity::class.java)
            i.putExtra("id", film.id)
            if(context is FragmentFilm)
                context.startActivityForResult(i, FragmentFilm.LAUNCH_ADD_ACTIVITY)
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Film> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(filmList)
                }else{
                    for (film in filmList){
                        if(film.judul.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))

                        )filtered.add(film)

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults

            }

            override fun publishResults( CharSequence: CharSequence, filterResults: FilterResults) {
                filteredFilmList.clear()
                filteredFilmList.addAll(filterResults.values as List<Film>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvJudul: TextView
        var tvGenre: TextView
        var tvRating: TextView
        var btnDelete: ImageButton
        var cvMahasiswa: CardView

        init {
            tvJudul = itemView.findViewById(R.id.tv_judul)
            tvGenre = itemView.findViewById(R.id.tv_genre)
            tvRating = itemView.findViewById(R.id.tv_rating)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvMahasiswa = itemView.findViewById(R.id.cv_mahasiswa)
        }

    }
}