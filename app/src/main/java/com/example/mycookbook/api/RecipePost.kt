package com.example.mycookbook.api

import android.text.SpannableString
import com.google.gson.annotations.SerializedName

data class RecipePost(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: SpannableString,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("imageType")
    val imageType: String,
) {

}
// {"results":[{"id":715769,"title":"Broccolini Quinoa Pilaf","image":"https://spoonacular.com/recipeImages/715769-312x231.jpg","imageType":"jpg"}