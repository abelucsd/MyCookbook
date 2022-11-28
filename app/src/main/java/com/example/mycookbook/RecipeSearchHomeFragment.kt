package com.example.mycookbook

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.databinding.RecipeSearchHomeFragmentBinding
import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeSearchHomeFragment : Fragment() {

    companion object{
        fun newInstance(): RecipeSearchHomeFragment {
            return RecipeSearchHomeFragment()
        }
    }
    private val viewModel: SearchViewModel by activityViewModels()
    private val favoriteRecipeIds = mutableListOf<Int>()

    lateinit var recipesDb: SQLiteDatabase
    lateinit var dbHelper: DatabaseHelper

    private var _binding: RecipeSearchHomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipeSearchHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as RecipeSearchActivity
        // val db = activity.recipesDb
        val rv = binding.searchRecyclerView
        val adapter = SearchRowAdapter(viewModel) //, activity.recipesDb, activity.dbHelper)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)

        /*
        dbHelper = DatabaseHelper(requireContext())
        try {
            dbHelper.createDatabase()
        } catch (e: IOException) {
            Log.e("DB", "Fail to create database", e)
        }
        val db = dbHelper.writableDatabase

         */

        /*
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_Second3Fragment)
        }*/
        /*
        db.beginTransaction()
        val testCv = ContentValues()
        testCv.put("id", 123)
        testCv.put("name", "Hello")
        testCv.put("api_id", 123)
        testCv.put("is_favorited", true)
        db.insertOrThrow("my_recipes", null, testCv)
        db.endTransaction()

         */


        viewModel.observeRecipes().observe(viewLifecycleOwner) {
            adapter.addRecipes(it)
            adapter.notifyDataSetChanged()
        }

        /*
        viewModel.observeFavoriteRecipe().observe(viewLifecycleOwner) {
            db.beginTransaction()
            // DO I need to begin and end transaction?
            // TODO: Note -- all items in db are 'favorited' items.
            Log.d("dbinput", "Item: ${it.title}, ${it.id}")
            val cv = ContentValues()
            cv.put("id", it.id)
            cv.put("name", it.title.toString())
            cv.put("api_id", it.id)
            cv.put("is_favorited", true)
            // dbHelper.addRecipe(item.title.toString(), item.id, true)
            db.insertOrThrow("my_recipes", null, cv)
            Log.d("dbinput", "Db input passed.")
            /*
            val cv = ContentValues()
            cv.put("is_favorited", "True")
             */
            db.endTransaction()
        }

         */



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}