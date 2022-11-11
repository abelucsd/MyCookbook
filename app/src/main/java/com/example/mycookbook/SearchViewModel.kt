package com.example.mycookbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycookbook.api.RecipeApi
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.api.RecipePostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = RecipePostRepository(RecipeApi.create())
    private var recipes = MutableLiveData<List<RecipePost>>()

    fun netRefresh(cuisine: String, protein: String) = viewModelScope.launch (
        context = viewModelScope.coroutineContext + Dispatchers.IO
            ) {
        // Pass in values
        recipes.postValue(repository.getRecipes(cuisine, protein))
    }
    fun observeRecipes(): LiveData<List<RecipePost>> {
        return recipes
    }

}