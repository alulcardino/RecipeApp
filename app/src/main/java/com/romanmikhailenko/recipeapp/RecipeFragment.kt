package com.romanmikhailenko.recipeapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.romanmikhailenko.recipeapp.databinding.FragmentFavoritesBinding
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipeBinding
import com.romanmikhailenko.recipeapp.model.Recipe


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val mBinding get() = _binding!!
    private var recipe : Recipe? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.tvRecipeName.text = recipe?.title
    }

}