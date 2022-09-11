package com.willychia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        changeFragment(FragmentFilm())

        val botNav : NavigationBarView = findViewById(R.id.bottom_navigation)

        botNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    changeFragment(FragmentFilm())
                    true
                }
                R.id.location -> {
                    changeFragment(FragmentFilm())
                    true
                }
                R.id.movie -> {
                    changeFragment(FragmentPengunjung())
                    true
                }
                R.id.profile -> {
                    exit()
                    true
                }
                else -> false
            }
        }
    }

    fun changeFragment(fragment: Fragment){
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.layout_fragment, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    fun exit(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@HomeActivity)
        builder.setMessage("Are you sure want to exit?")
            .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                override fun onClick(dialogInterface: DialogInterface, i: Int) {
                    finishAndRemoveTask()
                }
            })
            .setNegativeButton("NO", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }
            })
            .show()
    }
}