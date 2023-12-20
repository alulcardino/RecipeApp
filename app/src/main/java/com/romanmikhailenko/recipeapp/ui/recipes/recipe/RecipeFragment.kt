package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipeBinding
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE_ID
import java.lang.Exception
import java.lang.IllegalStateException

class  PortionSeekBarListener(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel.loadRecipe(requireArguments().getInt(ARG_RECIPE_ID))
        initUI(recipeViewModel.recipeState.value)
        initClickFavoriteButton(recipeViewModel.recipeState.value)
    }

    private fun initUI(recipeState: RecipeState?) {
        val methodAdapter = MethodAdapter(recipeState?.recipe?.method ?: listOf())
        val ingredientsAdapter = IngredientsAdapter(recipeState?.recipe?.ingredients ?: listOf())

        recipeViewModel.recipeState.observe(viewLifecycleOwner) {
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
            recipeState?.portions?.let { ingredientsAdapter.updateIngredients(it) }
            mBinding.sbPortions.setOnSeekBarChangeListener(PortionSeekBarListener {
                recipeViewModel.onChangePortions(it)
            })

        }
    }

    private fun initClickFavoriteButton(recipeState: RecipeState?) {
        val emptyIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart_empty) }
        val fulledIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart) }

        if (recipeState?.isFavorite == true) {
            mBinding.ibtnRecipeFavorite.setImageDrawable(fulledIcon)
        } else {
            mBinding.ibtnRecipeFavorite.setImageDrawable(emptyIcon)
        }
        with(mBinding.ibtnRecipeFavorite) {
            setOnClickListener {
                recipeViewModel.onFavoritesClicked()
                if (recipeState?.isFavorite == true) {
                    mBinding.ibtnRecipeFavorite.setImageDrawable(fulledIcon)
                } else {
                    mBinding.ibtnRecipeFavorite.setImageDrawable(emptyIcon)
                }
            }
        }
    }


}