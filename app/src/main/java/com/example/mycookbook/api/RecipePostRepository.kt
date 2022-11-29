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
        Log.d("API workflow", "unpackPosts()")

        var listing = mutableListOf<RecipePost>()
        // Log.d("API workflow", "unpackPosts() ${response.results}")
        for (item in response.results) {
            listing.add(item)
        }
        /*
        for (item in response.results) {
            listing.add(item.data)
        }*

         */
        Log.d("API workflow", "unpackPosts() listing")
        return listing
    }

    suspend fun getRecipes(cuisine: String, protein: String): List<RecipePost> {
        // if (RecipeSearchActivity.globalDebug) {
        Log.d("API workflow", "getRecipes() ${cuisine} ${protein}")
        val apiReturn = recipeApi.getRecipeList(cuisine)
        var listing = unpackPosts(apiReturn)

        Log.d("API workflow", "getRecipes() ${listing}")

        return listing
    }

    suspend fun getRecipeInfo(id: String): String {
        val apiReturn = recipeApi.getRecipeInfo(id)

        Log.d("recipeInfo", "ApiReturn: ${apiReturn.spoonacularSourceUrl}")
        return apiReturn.spoonacularSourceUrl
    }
}