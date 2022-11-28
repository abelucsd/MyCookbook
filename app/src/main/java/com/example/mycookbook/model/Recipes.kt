package com.example.mycookbook.model

data class Recipes(var id: Long,
                   var name: String,
                   var image_url: String,
                   var image_type: String
                   ) {

    companion object CREATOR {
        const val TABLE_NAME = "my_recipes"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_IMAGE_TYPE = "image_type"
        const val COLUMN_API_ID = "api_id"
        const val COLUMN_IS_FAVORITED = "is_favorited"
        const val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME + " NAME,"
                    + COLUMN_IMAGE_URL + " IMAGE_URL,"
                    + COLUMN_IMAGE_TYPE + " IMAGE_TYPE"
                    + ")")

    }
}
