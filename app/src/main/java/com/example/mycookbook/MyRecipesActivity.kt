package com.example.mycookbook

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
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

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyRecipesBinding
    // Maybe does this need to be in MainActivity? No.
    lateinit var recipesDb: SQLiteDatabase
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        dbHelper = DatabaseHelper(this)
        try {
            dbHelper.createDatabase()
        } catch (e: IOException) {
            Log.e("DB", "Fail to create database", e)
        }
        recipesDb = dbHelper.readableDatabase

        addHomeFragment()

        /*
        val navController = findNavController(R.id.nav_host_fragment_content_my_recipes)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
         */
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

    /*
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_my_recipes)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
     */
}