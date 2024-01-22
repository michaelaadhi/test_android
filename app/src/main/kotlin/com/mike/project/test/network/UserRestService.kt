package com.mike.project.test.network

import retrofit2.http.GET

interface UserRestService {
    @GET("users")
    fun getUsers()
}