package com.romanmikhailenko.recipeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val title: String
) : Parcelable