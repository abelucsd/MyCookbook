package com.example.mycookbook

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.databinding.RowSearchBinding


class SearchRowAdapter(private val viewModel: SearchViewModel, private val db: SQLiteDatabase)
    : ListAdapter<RecipePost, SearchRowAdapter.VH>(RecipeDiff()) {
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
                    // var favoriteList = item.getFavoriteRecipeList()
                    val args = ArrayList<String>()
                    val where = ArrayList<String>()
                    where.add("(my_recipes.is_favorited = ?)")
                    args.add("True")

                    // add more args filters later.

                    var table = "my_recipes"
                    var selectionStr = ""
                    if (where.size != 0) {
                        selectionStr += where[0]
                        for (i in 1 until where.size) {
                            selectionStr += " AND " + where[i]
                        }
                    }

                    var limitstr = "50"


                    // TODO: Perhaps change boolean to int 0 and 1
                    db.beginTransaction()
                    // what is a Cursor? Isn't it an iterator. don't need it. just need the list
                    val favorited_query = db.query(
                        table,
                        null,
                        selectionStr,
                        args.toTypedArray(),
                        null,
                        null,
                        null,
                        limitstr
                    )
                    db.endTransaction()
                    // turn the cursor into the list
                    // get the ids
                    val favorited_ids = mutableListOf<Int>()
                    // TODO: could optimize this. instead of creating a new list. add onto an existing list?
                    try {
                        favorited_query.moveToFirst()
                        while (favorited_query.moveToNext()) {
                            favorited_query.getInt(favorited_query.getColumnIndexOrThrow("api_id"))
                            favorited_ids.add(favorited_query.getInt(favorited_query.getColumnIndexOrThrow("api_id")))
                        }
                    } finally {
                        favorited_query.close()
                    }

                    if (favorited_ids.contains(item.id)) {
                        /*
                        UPDATE my_recipes SET is_favorited="False" WHERE api_id = item.id
                        db.update(table, "is_favorited" + " VALUES 'False' ", " WHERE api_id = item.id", null);

                        Make ContentValues object:
                        ContentValues cv = new ContentValues()
                        cv.put("is_favorited", "False");
                        db.update(table, cv, "p_id = ?", item.id);
                         */
                        db.beginTransaction()
                        val cv = ContentValues()
                        cv.put("is_favorited", "False")
                        db.endTransaction()
                        // or cv.put("is_favorited", "0")
                        binding.recipeCardFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    } else {
                        db.beginTransaction()
                        val cv = ContentValues()
                        cv.put("is_favorited", "True")
                        db.endTransaction()
                        // or cv.put("is_favorited", "1")
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



    }

    fun addRecipes(items: List<RecipePost>) {
        recipes.addAll(items)
    }

    fun clear() {
        recipes.clear()
    }

    override fun getItemCount() = recipes.size
}
