package com.example.coingeckoapi.data.roomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coingeckoapi.data.roomdb.CoinEntity
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import com.example.coingeckoapi.data.roomdb.dao.TransactionDao
import com.example.coingeckoapi.data.roomdb.dao.UserDao
import com.example.coingeckoapi.data.roomdb.entity.TransactionEntity
import com.example.coingeckoapi.data.roomdb.entity.UserEntity


@Database(entities = [CoinEntity::class, UserEntity::class, TransactionEntity::class], version = 1, exportSchema = false)
abstract class CoinDB : RoomDatabase() {

    abstract fun coinDao(): CoinDao
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
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