package com.romanmikhailenko.recipeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipeBinding
import com.romanmikhailenko.recipeapp.ui.adapters.IngredientsAdapter
import com.romanmikhailenko.recipeapp.ui.adapters.MethodAdapter
import com.romanmikhailenko.recipeapp.ui.viewmodels.RecipeState
import com.romanmikhailenko.recipeapp.ui.viewmodels.RecipeViewModel
import com.romanmikhailenko.recipeapp.ui.adapters.RecyclerViewItemDecoration
import java.lang.IllegalStateException

class PortionSeekBarListener(
    val onChangeIngredients: (Int) -> Unit

) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChangeIngredients(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

}

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val recipeViewModel: RecipeViewModel by viewModels()
    private val recipeFragmentArgs : RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel.loadRecipe(recipeFragmentArgs.recipeId)
        initUI(recipeViewModel.recipeState.value)
    }

    private fun initUI(recipeState: RecipeState?) {
        val methodAdapter = MethodAdapter(recipeState?.recipe?.method ?: listOf())
        val ingredientsAdapter = IngredientsAdapter(recipeState?.recipe?.ingredients ?: listOf())

        recipeViewModel.recipeState.observe(viewLifecycleOwner) { state ->
            Log.i("!!!", recipeViewModel.recipeState.value?.isFavorite.toString())

            mBinding.tvRecipeTitle.text = recipeState?.recipe?.title
            mBinding.ivRecipe.setImageDrawable(recipeState?.recipeDrawable)

            val divider = RecyclerViewItemDecoration(this.context, R.drawable.divider)
            with(mBinding.rvMethod) {
                addItemDecoration(divider)
                adapter = methodAdapter
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            }
            with(mBinding.rvIngredients) {
                addItemDecoration(divider)
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            }
            mBinding.sbPortions.setOnSeekBarChangeListener(PortionSeekBarListener {
                recipeViewModel.onChangePortions(it)
                ingredientsAdapter.updateIngredients(it)
                mBinding.tvPortionsCount.text = it.toString()
            })
            val emptyIcon =
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart_empty) }
            val fulledIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart) }

            if (state.isFavorite) {
                mBinding.ibtnRecipeFavorite.setImageDrawable(fulledIcon)
            } else {
                mBinding.ibtnRecipeFavorite.setImageDrawable(emptyIcon)
            }
            with(mBinding.ibtnRecipeFavorite) {
                setOnClickListener {
                    recipeViewModel.onFavoritesClicked()
                }
            }
        }

    }

}