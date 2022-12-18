package com.willychia.TugasBesar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.willychia.TugasBesar.databinding.FragmentLocationBinding
import com.willychia.TugasBesar.databinding.FragmentPengunjungBinding

class FragmentLocation :Fragment(){
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences?=null
    private var binding1: FragmentLocationBinding?=null
    private val binding get() = binding1!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding1 = FragmentLocationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val intent = Intent(activity, LocationActivity::class.java)
            startActivity(intent)
    }
}