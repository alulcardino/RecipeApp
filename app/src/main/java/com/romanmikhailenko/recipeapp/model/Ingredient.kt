package com.romanmikhailenko.recipeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val description: String,
    val quantity: String,
    val unitOfMeasure: String
) : Parcelable