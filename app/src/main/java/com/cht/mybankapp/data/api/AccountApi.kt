package com.cht.mybankapp.data.api

import com.cht.mybankapp.data.model.Account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Интерфейс для работы с API счетов
interface AccountApi {

    // Метод для получения списка счетов (HTTP GET запрос)
    @GET("accounts")
    fun getAccounts(): Call<List<Account>>

    // Метод для создания нового счета (HTTP POST запрос)
    @POST("accounts")
    fun createAccount(@Body account: Account): Call<Account>
}