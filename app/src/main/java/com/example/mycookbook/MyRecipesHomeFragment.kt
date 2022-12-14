package com.example.mycookbook

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.api.RecipePost
import com.example.mycookbook.databinding.MyRecipesHomeFragmentBinding

class MyRecipesHomeFragment : Fragment() {

    companion object{
        fun newInstance(): MyRecipesHomeFragment {
            return MyRecipesHomeFragment()
        }
    }
    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: MyRecipesHomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MyRecipesHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MyRecipesActivity

        val rv = binding.myRecipesRecyclerView
        val adapter = MyRecipesRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)

        // add recipes through db
        adapter.addRecipes(viewModel.getFavoriteRecipes())
        adapter.notifyDataSetChanged()

        // go to webpage
        viewModel.observeRecipeUrl().observe(viewLifecycleOwner) {
            if (it != "") {
                val openURL = Intent(android.content.Intent.ACTION_VIEW)
                openURL.data = Uri.parse(it)
                startActivity(openURL)
            }
        }
    }

    private fun populateRows(db: SQLiteDatabase) : List<RecipePost>  {
        // val item = recipes[adapterPosition]
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
        val recipePosts = mutableListOf<RecipePost>()
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

        return recipePosts
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}