package com.example.mycookbook

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.databinding.RowSearchBinding

// I dont need a view model. I use the database.
class MyRecipesRowAdapter(private val viewModel: SearchViewModel)
    : ListAdapter<RecipePost, MyRecipesRowAdapter.VH>(RecipeDiff()) {
    class RecipeDiff : DiffUtil.ItemCallback<RecipePost>() {
        override fun areContentsTheSame(oldItem: RecipePost, newItem: RecipePost): Boolean {
            TODO("Not yet implemented")
        }

        override fun areItemsTheSame(oldItem: RecipePost, newItem: RecipePost): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private var recipes = mutableListOf<RecipePost>()

    inner class VH(val binding: RowSearchBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.recipeCardFavoriteIcon.setOnClickListener {
                val item = recipes[adapterPosition]
                //viewModel.addFaveRecipe(item)
                //viewModel.setFavoriteRecipe(item)
                //val fave_recipes = viewModel.getFavoriteRecipes()
                val ids = viewModel.getFaveRecipes()

                if (ids.contains(item.id.toLong())) {
                    viewModel.deleteFaveRecipe(item)
                    binding.recipeCardFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    viewModel.addFaveRecipe(item)
                    binding.recipeCardFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RowSearchBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = recipes[holder.adapterPosition]
        val rowBinding = holder.binding
        rowBinding.recipeTitle.text = item.title
        // Glide Image

        // On Click to view one recipe page.
        val ids = viewModel.getFaveRecipes()
        if (ids.contains(item.id.toLong())) {
            rowBinding.recipeCardFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            rowBinding.recipeCardFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }


    }

    fun addRecipes(items: List<RecipePost>) {
        recipes.addAll(items)
    }

    fun clear() {
        recipes.clear()
    }

    override fun getItemCount() = recipes.size
}
