package com.romanmikhailenko.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.romanmikhailenko.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val fragment: Fragment = CategoriesListFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.mainContainer, fragment)
            .commit()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}