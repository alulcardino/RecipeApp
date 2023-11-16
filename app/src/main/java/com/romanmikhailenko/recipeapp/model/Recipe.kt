package com.romanmikhailenko.recipeapp.model

data class Recipe(
    val id: Int,
    val imageUrl: String,
    val ingredient: List<Ingredient>,
    val method: List<String>,
    val title: String
)