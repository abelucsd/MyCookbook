package com.example.mycookbook

import android.content.Context
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.model.Recipes

class ViewModelDbHelper (appContext: Context){
    private var db: DatabaseHelper = DatabaseHelper(appContext)

    fun addRecipe(recipe: RecipePost) {
        db.addRecipe(recipe)
    }
    fun deleteRecipe(recipe: RecipePost) {
        db.removeRecipe(recipe.id)
    }
    fun getFaveRecipeIds(): List<Long> {
        return db.allRecipes
    }
    fun getFaveRecipes(): List<RecipePost> {
        return db.allRecipePosts
    }
}