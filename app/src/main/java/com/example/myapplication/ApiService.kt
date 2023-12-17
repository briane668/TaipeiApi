package com.example.myapplication

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext





import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*



/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://www.travel.taipei/open-api/")
    .client(client)
    .build()


interface TaipeiTravelApi {
    @Headers("Accept: application/json")
    @GET("{lang}/Attractions/All")
    suspend fun getAttractionsAsync(
        @Path("lang") lang: String
    ): AttractionResponse


    @Headers("Accept: application/json")
    @GET("{lang}/Events/News")
    suspend fun getEventsAsync(
        @Path("lang") lang: String
    ): EventsResponse

}


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object StylishApi {

    private val taipeiTravelApi : TaipeiTravelApi by lazy { retrofit.create(TaipeiTravelApi::class.java) }



    suspend fun getAttractions(lang: String): List<AttractionData> {
        return withContext(Dispatchers.IO) {
            val response = taipeiTravelApi.getAttractionsAsync(lang)
            response.data
        }
    }
}


