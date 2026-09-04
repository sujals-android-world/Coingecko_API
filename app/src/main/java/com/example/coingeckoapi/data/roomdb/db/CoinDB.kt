package com.example.coingeckoapi.data.roomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coingeckoapi.data.roomdb.CoinEntity
import com.example.coingeckoapi.data.roomdb.dao.CoinDao


@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDB : RoomDatabase() {

    abstract fun coinDao(): CoinDao
    companion object {
        @Volatile
        private var INSTANCE: CoinDB? = null

        fun getDatabase(context: Context): CoinDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinDB::class.java,
                    "coin_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}