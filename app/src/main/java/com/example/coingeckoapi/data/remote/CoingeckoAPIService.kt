package com.example.coingeckoapi.data.remote

import com.example.coingeckoapi.data.remote.response.CoinGeckoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoingeckoAPIService {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") currency: String = "inr",
        @Query("per_page") perPage: Int = 10,
        @Header("x_cg_demo_api_key") apiKey: String = "CG-1UR8yDLvtwZWX9NodJhPiSoU"
    ): CoinGeckoResponse
}