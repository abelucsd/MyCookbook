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
import android.content.Intent

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


        viewModel.observeRecipes().observe(viewLifecycleOwner) {
            adapter.addRecipes(it)
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}