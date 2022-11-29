package com.example.mycookbook.api

import com.google.gson.annotations.SerializedName

data class RecipeInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String,
)