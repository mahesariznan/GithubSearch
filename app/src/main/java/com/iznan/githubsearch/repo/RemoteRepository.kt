package com.iznan.githubsearch.repo

import com.iznan.githubsearch.services.RemoteService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RemoteRepository {

    val service: RemoteService

    companion object {
        const val BASE_API = "https://api.github.com"

        @Volatile
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(): RemoteRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteRepository().also { INSTANCE = it }
            }
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(buildOkHttp())
            .build()

        service = retrofit.create(RemoteService::class.java)
    }

    fun api(): RemoteService {
        return service
    }

    private fun buildOkHttp(): OkHttpClient {
        val interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val newRequest = chain.request().newBuilder().build()
                return chain.proceed(newRequest)
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}