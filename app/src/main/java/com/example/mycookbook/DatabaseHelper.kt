package com.example.mycookbook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import com.example.mycookbook.api.RecipePost
import java.io.FileOutputStream
import java.io.IOException
import com.example.mycookbook.model.Recipes


class DatabaseHelper(private val context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object {
        private const val DB_NAME = "recipes.db"
        val ID_COL = "id"
        val TABLE_NAME = "my_recipes"
        val NAME_COL = "name"
        val API_ID_COL = "api_id"
        val IS_FAVORITED_COL = "is_favorited"
        val IMAGE_URL = "IMAGE_URL"
        val IMAGE_TYPE ="IMAGE_TYPE"
    }

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(Recipes.CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS " + Recipes.TABLE_NAME)
        onCreate(p0)
    }

    // Add data to db
    fun addRecipe(item: RecipePost) {
        val values = ContentValues()

        //values.put(ID_COL, apiId)
        values.put(ID_COL, item.id)
        values.put(NAME_COL, item.title.toString())
        values.put(IMAGE_URL, item.imageUrl.toString())
        values.put(IMAGE_TYPE, item.imageType.toString())

        val db = this.writableDatabase

        db.insert(Recipes.TABLE_NAME, null, values)

        db.close()
    }

    // Remove data
    fun removeRecipe(apiId: Int) {
        val values = ContentValues()

        val db = this.writableDatabase
        val item = ArrayList<String>()
        item.add(apiId.toString())
        db.delete(TABLE_NAME, "id=?", item.toTypedArray())
        db.close()
    }

    val allRecipes: List<Long>
        get() {
            val db = this.readableDatabase
            val recipeCursor = db.query(Recipes.TABLE_NAME, null, null, null, null, null, Recipes.COLUMN_ID + " DESC")
            val recipeList = mutableListOf<Long>()
            if (recipeCursor.count != 0) {
                if (recipeCursor.moveToFirst()) {
                    do {
                        val id = recipeCursor.getLong(recipeCursor.getColumnIndexOrThrow(Recipes.COLUMN_ID))
                        recipeList.add(id)
                    } while (recipeCursor.moveToNext())
                }
            }
            recipeCursor.close()
            return recipeList
        }

    val allRecipePosts: List<RecipePost>
        get() {
            val db = this.readableDatabase
            val recipeCursor = db.query(Recipes.TABLE_NAME, null, null, null, null, null, Recipes.COLUMN_ID + " DESC")
            val recipePosts = mutableListOf<RecipePost>()
            if (recipeCursor.count != 0) {
                if (recipeCursor.moveToFirst()) {
                    do {
                        val recipePost = RecipePost(recipeCursor.getLong(recipeCursor.getColumnIndexOrThrow(Recipes.COLUMN_ID)).toInt(), recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(Recipes.COLUMN_NAME)), recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(Recipes.COLUMN_IMAGE_URL)), recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(Recipes.COLUMN_IMAGE_TYPE)))
                        recipePosts.add(recipePost)
                    } while (recipeCursor.moveToNext())
                }
            }
            recipeCursor.close()
            return recipePosts
        }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }
}