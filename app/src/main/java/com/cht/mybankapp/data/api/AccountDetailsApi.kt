package com.cht.mybankapp.data.api

import com.cht.mybankapp.data.model.Account
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountDetailsApi {

    @GET("accounts/{id}")
    suspend fun getAccountDetails(@Path("id") id: String): Account


}