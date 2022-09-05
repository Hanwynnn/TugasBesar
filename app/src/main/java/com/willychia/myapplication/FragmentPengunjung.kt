package com.willychia.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willychia.myapplication.entity.Pengunjung

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class FragmentPengunjung : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pengunjung, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVPengunjungAdapter = RVPengunjungAdapter(Pengunjung.listOfPengunjung)

        val rvPengunjung : RecyclerView = view.findViewById(R.id.rv_pengunjung)

        rvPengunjung.layoutManager = layoutManager

        rvPengunjung.setHasFixedSize(true)

        rvPengunjung.adapter = adapter
    }
}