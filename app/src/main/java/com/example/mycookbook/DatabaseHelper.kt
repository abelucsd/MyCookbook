package com.example.mycookbook

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException


class DatabaseHelper(private val context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object {
        private const val DB_NAME = "recipes.db"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val input = context.assets.open(DB_NAME)
        val outFile = context.getDatabasePath(DB_NAME)
        val output = FileOutputStream(outFile)
        input.copyTo(output)
    }

    @Throws(IOException::class)
    fun createDatabase() {
        val inFile = context.getDatabasePath(DB_NAME)
        if (!inFile.exists()) {
            copyDatabase()
        }
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }
}