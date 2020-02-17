package com.iznan.githubsearch.services

import com.iznan.githubsearch.model.ResponseUsers
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("https://api.github.com/search/users")
    fun getUsers(@Query("q") user_name: String, @Query("page") page: Int, @Query("per_page") per_page: Int): Single<ResponseUsers>

}