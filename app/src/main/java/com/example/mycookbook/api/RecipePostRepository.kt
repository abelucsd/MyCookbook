package com.example.mycookbook.api

import android.text.SpannableString
import android.util.Log
import com.example.mycookbook.RecipeSearchActivity
import com.google.gson.GsonBuilder

class RecipePostRepository(private val recipeApi: RecipeApi) {
    val gson = GsonBuilder().registerTypeAdapter(
        SpannableString::class.java, RecipeApi.SpannableDeserializer()
    ).create()

    private fun unpackPosts(response: RecipeApi.ListingResponse): List<RecipePost> {
        var listing = mutableListOf<RecipePost>()
        for (item in response.data.children) {
            listing.add(item.data)
        }
        return listing
    }

    suspend fun getRecipes(cuisine: String, protein: String): List<RecipePost> {
        // if (RecipeSearchActivity.globalDebug) {
        Log.d("API workflow", "getRecipes() ${cuisine} ${protein}")
        var listing = unpackPosts(recipeApi.getRecipeList(cuisine))

         return listing
    }
}