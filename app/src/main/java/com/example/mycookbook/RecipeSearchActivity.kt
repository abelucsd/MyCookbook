package com.example.mycookbook

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mycookbook.databinding.ActivityRecipeSearchBinding
import androidx.fragment.app.FragmentTransaction
import com.example.mycookbook.RecipeSearchHomeFragment
import androidx.fragment.app.commit

class RecipeSearchActivity : AppCompatActivity() {

    companion object  {
        var globalDebug = false
        private const val homeFragTag = "homeFragTag"
        private const val recipeOneTag = "recipeOneTag"
    }
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRecipeSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        addHomeFragment()


        /*
        val navController = findNavController(R.id.main_search_frame)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

         */
    }

    // Home Frag
    private fun addHomeFragment() {
        supportFragmentManager.commit {
            add(R.id.main_search_frame, RecipeSearchHomeFragment.newInstance(), homeFragTag)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_search_frame)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}