package com.cht.mybankapp.data.api

import com.cht.mybankapp.data.model.Account
import com.cht.mybankapp.data.model.PatchAccountStatusDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    @GET("accounts")
    fun getAccounts(): Call<List<Account>>

    @POST("accounts")
    fun createAccount(@Body account: Account): Call<Account>

    @DELETE("accounts/{id}")
    fun deleteAccount(@Path ("id") id: String): Call<Unit>

    @PUT("accounts/{id}")
    fun updateAccountFully(
        @Path ("id") id: String,
        @Body account: Account
    ): Call<Account>

    @PATCH("accounts/{id}")
    fun patchAccountStatus(
        @Path ("id") id: String,
        @Body patchAccountStatusDTO: PatchAccountStatusDTO
    ): Call<Account>


}