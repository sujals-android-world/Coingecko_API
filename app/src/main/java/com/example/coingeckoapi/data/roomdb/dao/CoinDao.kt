package com.example.coingeckoapi.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.coingeckoapi.data.roomdb.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {


    @Upsert
    suspend fun upsertCoin(coinEntity: List<CoinEntity>)

    @Query("SELECT * FROM coin_table")
    fun getAllCoin() : Flow<List<CoinEntity>>

    @Query("select * from coin_table where id  = :id")
    suspend fun getCoinById(id : String): CoinEntity?

    @Query("select * from coin_table where id In (:ids)")
    fun getCoinsById(ids : Set<String>) : Flow<List<CoinEntity>>

}