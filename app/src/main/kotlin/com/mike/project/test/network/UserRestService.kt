package com.mike.project.test.network

import com.mike.project.test.bean.GetUsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserRestService {
    @GET("users")
    fun getUsers(): Call<GetUsersResponse>
}