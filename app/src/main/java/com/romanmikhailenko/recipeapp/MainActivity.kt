package com.romanmikhailenko.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.romanmikhailenko.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val fragmentTransaction: android.app.FragmentTransaction? = fragmentManager.beginTransaction()
        fragmentTransaction?.commit()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}