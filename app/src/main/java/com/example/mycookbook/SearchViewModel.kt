package com.example.mycookbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycookbook.api.RecipeApi
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.api.RecipePostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mycookbook.ViewModelDbHelper
import com.example.mycookbook.DatabaseHelper

class SearchViewModel : ViewModel() {
    private lateinit var dbHelp : ViewModelDbHelper
    private val favoriteRecipeIds = MutableLiveData<List<Int>>()
    private val favoriteRecipe = MutableLiveData<RecipePost>()
    private val repository = RecipePostRepository(RecipeApi.create())
    private var recipes = MutableLiveData<List<RecipePost>>()
    private val proteinSearch = MutableLiveData<String>().apply {
        value = "chicken"
    }
    private var cuisineSearch = MutableLiveData<String>().apply {
        value = "american"
    }

    init {
        repoFetch()
    }

    fun initDBHelper(dbHelper: ViewModelDbHelper) {
        dbHelp = dbHelper

    }

    fun repoFetch() {
        netRefresh(cuisineSearch.value.toString(), proteinSearch.value.toString())
    }

    fun netRefresh(cuisine: String, protein: String) = viewModelScope.launch (
        context = viewModelScope.coroutineContext + Dispatchers.IO
            ) {
        // Pass in values
        Log.d("ViewModel", "netRefresh()")
        recipes.postValue(repository.getRecipes(cuisine, protein))
    }
    fun observeRecipes(): LiveData<List<RecipePost>> {
        return recipes
    }

    fun observeFavoriteRecipeIds(): LiveData<List<Int>> {
        return favoriteRecipeIds
    }

    fun setFavoriteRecipeIds(items: List<Int>) {
        favoriteRecipeIds.value = items
    }

    fun observeFavoriteRecipe(): LiveData<RecipePost> {
        return favoriteRecipe
    }

    fun setFavoriteRecipe(item: RecipePost) {
        favoriteRecipe.value = item
    }

    fun addFaveRecipe(item: RecipePost) {
        dbHelp.addRecipe(item)
    }
    fun getFaveRecipes(): List<Long> {
        return dbHelp.getFaveRecipeIds()
    }
    fun deleteFaveRecipe(item: RecipePost) {
        return dbHelp.deleteRecipe(item)
    }
    fun getFavoriteRecipes(): List<RecipePost> {
        return dbHelp.getFaveRecipes()
    }
}