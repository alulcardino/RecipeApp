package com.romanmikhailenko.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.romanmikhailenko.recipeapp.R.id.mainContainer
import com.romanmikhailenko.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(mainContainer)
            addToBackStack(null)
        }

        mBinding.btnCategory.setOnClickListener {
            supportFragmentManager.commit {
                replace<FavoritesFragment>(mainContainer)
                setReorderingAllowed(true)
            }
        }

        mBinding.btnFavorite.setOnClickListener {
            supportFragmentManager.commit {
                replace<CategoriesListFragment>(mainContainer)
                setReorderingAllowed(true)            }
        }
    }
}