package com.example.mycookbook

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import com.example.mycookbook.databinding.ActivityMainBinding
import com.example.mycookbook.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding
    /*
    TODO:
    - Intent to SelectCriteria activity
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val actionToolBar = binding.toolbar
        setSupportActionBar(actionToolBar)
        actionToolBar.setTitle("My Cookbook")

        contentMainBinding = binding.contentMain

        // My Code
        contentMainBinding.toSelectCriteriaActivity.setOnClickListener {
            navigateToSearchCriteria()
        }

        contentMainBinding.toMyRecipesActivity.setOnClickListener {
            navigateToMyRecipes()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSearchCriteria() {
        // Create an Intent
        val getIntent = Intent(this, SelectCriteriaActivity::class.java)
        try {
            startActivity(getIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d(javaClass.simpleName, "navigateToSearchCriteria() - ActivityNotFound - ${e}")
        }
    }

    private fun navigateToMyRecipes() {
        val getIntent = Intent(this, MyRecipesActivity::class.java)
        try {
            startActivity(getIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d(javaClass.simpleName, "navigateToMyRecipes() - ActivityNotFound - ${e}")
        }
    }
}