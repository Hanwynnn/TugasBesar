package com.willychia.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

//        changeFragment(FragmentFilm())

        val botNav : NavigationBarView = findViewById(R.id.bottom_navigation)

        botNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {

                    true
                }
                R.id.location -> {

                    true
                }
                R.id.movie -> {
                    changeFragment(FragmentFilm())
                    true
                }
                R.id.profile -> {
                    changeFragment(FragmentPengunjung())
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
}