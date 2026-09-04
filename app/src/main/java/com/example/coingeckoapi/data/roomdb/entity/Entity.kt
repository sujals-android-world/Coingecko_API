package com.example.coingeckoapi.data.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("coin_table")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "ath")
    val ath: Double,
    @ColumnInfo(name = "ath_change_percentage")
    val ath_change_percentage: Double,
    @ColumnInfo(name = "ath_date")
    val ath_date: String,
    @ColumnInfo(name = "atl")
    val atl: Double,
    @ColumnInfo(name = "atl_change_percentage")
    val atl_change_percentage: Double,
    @ColumnInfo(name = "atl_date")
    val atl_date: String,
    @ColumnInfo(name = "circulating_supply")
    val circulating_supply: Double?,
    @ColumnInfo(name = "current_price")
    val current_price: Double,
    @ColumnInfo(name = "fully_diluted_valuation")
    val fully_diluted_valuation: Long?,
    @ColumnInfo(name = "high_24h")
    val high_24h: Double,
    val image: String,
    @ColumnInfo(name = "last_updated")
    val last_updated: String,
    @ColumnInfo(name = "low_24h")
    val low_24h: Double,
    @ColumnInfo(name = "market_cap")
    val market_cap: Long,
    @ColumnInfo(name = "market_cap_change_24h")
    val market_cap_change_24h: Double,
    @ColumnInfo(name = "market_cap_change_percentage_24h")
    val market_cap_change_percentage_24h: Double,
    @ColumnInfo(name = "market_cap_rank")
    val market_cap_rank: Int,
    @ColumnInfo(name = "max_supply")
    val max_supply: Double?,
    val name: String,
    @ColumnInfo(name = "price_change_24h")
    val price_change_24h: Double,
    @ColumnInfo(name = "price_change_percentage_24h")
    val price_change_percentage_24h: Double,
    val symbol: String,
    @ColumnInfo(name = "total_supply")
    val total_supply: Double?,
    @ColumnInfo(name = "total_volume")
    val total_volume: Long
)
