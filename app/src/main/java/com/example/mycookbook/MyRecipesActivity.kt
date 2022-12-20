package com.example.mycookbook

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mycookbook.databinding.ActivityMyRecipesBinding
import java.io.IOException

/*
*   Should go to a fragment?
*   Need a recyclerview and an adapter.
* */
class MyRecipesActivity : AppCompatActivity() {

    companion object {
        private const val homeFragTag = "homeFragTag"
        private const val recipeOneTag = "recipeOneTag"
    }
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyRecipesBinding
    // Maybe does this need to be in MainActivity? No.
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        viewModel.initDBHelper(ViewModelDbHelper(this))


        addHomeFragment()


    }

    // Home Frag
    // TODO: What is main_search_frame again?
    private fun addHomeFragment() {
        supportFragmentManager.commit {
            add(R.id.main_recipe_frame, MyRecipesHomeFragment.newInstance(),
                homeFragTag
            )
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

}