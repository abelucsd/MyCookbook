package com.example.mycookbook

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mycookbook.databinding.ActivitySelectCriteriaBinding
import com.example.mycookbook.databinding.ContentMainBinding
import com.example.mycookbook.databinding.ContentSelectCriteriaBinding

class SelectCriteriaActivity : AppCompatActivity() {

    companion object {
        const val cuisineKey = "cuisineKey"
        const val proteinKey = "proteinKey"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySelectCriteriaBinding
    private lateinit var contentMainBinding: ContentSelectCriteriaBinding

    private val cuisineTypes: Array<String> by lazy {
        resources.getStringArray(R.array.cuisine_type)
    }
    private val cuisineTypesDescription: Array<String> by lazy {
        resources.getStringArray(R.array.cuisine_type_description)
    }
    private val proteinTypes: Array<String> by lazy {
        resources.getStringArray(R.array.protein_type)
    }
    private var selectedCuisine = null
    private var selectedProtein = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectCriteriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        contentMainBinding = binding.selectCriteriaContentMain

        // Spinner
        val cuisineTypeAdapter = ArrayAdapter.createFromResource(this,
            R.array.cuisine_type,
            android.R.layout.simple_spinner_item)
        cuisineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        contentMainBinding.cuisineTypeSpinner.adapter = cuisineTypeAdapter

        val proteinTypeAdapter = ArrayAdapter.createFromResource(this,
            R.array.protein_type,
            android.R.layout.simple_spinner_item)
        proteinTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        contentMainBinding.proteinTypeSpinner.adapter = proteinTypeAdapter

        // Selected Item
        contentMainBinding.cuisineTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCuisine = parent?.getItemAtPosition(position).toString()
                if (selectedCuisine != "--") {
                    contentMainBinding.cuisineSelectedDescription.text = cuisineTypesDescription[position]
                    contentMainBinding.selectedCuisine.text = selectedCuisine + " "
                }

            }
        }
        contentMainBinding.proteinTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProtein = parent?.getItemAtPosition(position).toString()
                if (selectedProtein != "--") {
                    contentMainBinding.selectedProtein.text = selectedProtein
                }
            }
        }

        // Button Find Recipes
        contentMainBinding.toSearchRecipesList.setOnClickListener {
            if (contentMainBinding.cuisineTypeSpinner.selectedItemPosition != 0) {
                navigateToRecipeSearch()
            } else {
                Snackbar.make(contentMainBinding.contentSelectCriteriaLayout,
                    "Select a cuisine",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        /*
        val navController = findNavController(R.id.nav_host_fragment_content_select_criteria)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
         */
    }

    /*
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_select_criteria)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
     */

    private fun navigateToRecipeSearch() {
        // Create an Intent
        val getIntent = Intent(this, RecipeSearchActivity::class.java)
        getIntent.putExtra(cuisineKey, contentMainBinding.selectedCuisine.text.toString())
        getIntent.putExtra(proteinKey, contentMainBinding.selectedProtein.text.toString())
        try {
            startActivity(getIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d(javaClass.simpleName, "navigateToRecipeSearchList() - ActivityNotFound - ${e}")
        }
    }

}