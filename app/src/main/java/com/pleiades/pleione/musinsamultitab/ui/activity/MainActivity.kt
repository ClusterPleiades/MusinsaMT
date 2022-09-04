package com.pleiades.pleione.musinsamultitab.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pleiades.pleione.musinsamultitab.R
import com.pleiades.pleione.musinsamultitab.ui.fragment.MusinsaFragment

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add musinsa fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_view, MusinsaFragment.newInstance()).commit()
    }
}