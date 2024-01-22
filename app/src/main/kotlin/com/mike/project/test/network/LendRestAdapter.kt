package com.mike.project.test.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LendRestAdapter {
    private const val BASE_URL = "https://reqres.in/api/"

    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 60L
    private const val CALL_TIMEOUT = 60L

    fun <T> createRestService(objClass: Class<T>): T {
        val client = okHttpClient
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
        return retrofit.create(objClass)
    }

    private val okHttpClient: OkHttpClient
        get() = defaultOkHttpBuilder.build()

    private val defaultOkHttpBuilder: OkHttpClient.Builder
        get() = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)

    private val gsonConverterFactory: GsonConverterFactory
        get() {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            return GsonConverterFactory.create(gson)
        }
}