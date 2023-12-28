package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romanmikhailenko.recipeapp.model.Recipe
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES_KEY

data class FavoriteState(
    var recipes: List<Recipe> = listOf()
)

class FavoriteViewModel(private val application: Application) : AndroidViewModel(application) {
    private var _favoriteState = MutableLiveData(FavoriteState())
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState

    fun loadFavorites() {
        _favoriteState.value?.recipes = getFavoritesById()
    }


    private fun getFavorites(): MutableSet<String> {
        val sharedPref = application.getSharedPreferences(PREFERENCE_FAVORITES, Context.MODE_PRIVATE)
        return HashSet(
            sharedPref?.getStringSet(PREFERENCE_FAVORITES_KEY, HashSet()) ?: setOf()
        )
    }

    private fun getFavoritesById(): List<Recipe> {
        val ids = getFavorites().map {
            it.toInt()
        }
        val favorites = mutableListOf<Recipe>()
        ids.forEach {
            favorites.add(STUB.getRecipeById(it))
        }
        return favorites
    }


}