package com.example.mycookbook.api
import android.text.SpannableString
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.HttpUrl
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type

interface RecipeApi {

    @GET("/recipes/complexSearch?number=15&apiKey=43f106e3bd704dbfb8a7d9aba2a3ae8d")
    suspend fun getRecipeList(@Query("cuisine") cuisine: String, @Query("includeIngredients") protein: String ): ListingResponse

    class ListingResponse(val results: List<RecipePost>)

    @GET("/recipes/{id}/information?includeNutrition=false&apiKey=43f106e3bd704dbfb8a7d9aba2a3ae8d")
    suspend fun getRecipeInfo(@Path("id") id: String): ApiRecipeInfo

    class ApiRecipeInfo(val spoonacularSourceUrl: String)


    //data class ListingData(val data: RecipePost)



    class ListingData(
        val children: List<RecipeChildrenResponse>
    )



    data class RecipeChildrenResponse(val data: RecipePost)

    class SpannableDeserializer : JsonDeserializer<SpannableString> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): SpannableString {
            return SpannableString(json.asString)
        }
    }

    companion object {
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder().registerTypeAdapter(
                SpannableString::class.java, SpannableDeserializer()
            )
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("api.spoonacular.com")
            .build()
        fun create(): RecipeApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): RecipeApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                //.addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApi::class.java)
        }
    }
}