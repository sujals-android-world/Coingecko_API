package com.example.coingeckoapi.di

import android.content.Context
import androidx.room.Room
import com.example.coingeckoapi.data.remote.CoingeckoAPIService
import com.example.coingeckoapi.data.repositoryImpl.CoinRepositoryImpl
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import com.example.coingeckoapi.data.roomdb.db.CoinDB
import com.example.coingeckoapi.domain.repository.CoinRepository
import com.example.coingeckoapi.domain.usacases.CoinUseCases
import com.example.coingeckoapi.domain.usacases.GetAllCoin
import com.example.coingeckoapi.domain.usacases.GetCoinByIdUseCase
import com.example.coingeckoapi.domain.usacases.RefreshCoinUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"

        @Provides
        @Singleton
        fun provideRetrofit() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideCoingeckoAPIService(retrofit: Retrofit): CoingeckoAPIService {
            return retrofit.create(CoingeckoAPIService::class.java)
        }

        @Provides
        @Singleton
        fun provideCoinUseCases(repository: CoinRepository) : CoinUseCases {
            return CoinUseCases(
                getCoin = GetAllCoin(repository = repository),
                refreshCoins = RefreshCoinUseCase(repository),
                getCoinById = GetCoinByIdUseCase(repository),
            )
        }

        @Provides
        @Singleton
        fun provideCoinDB(@ApplicationContext context: Context): CoinDB {
            return Room.databaseBuilder(
                context,
                CoinDB::class.java,
                "expense_db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideCoinDao(db: CoinDB) : CoinDao {
            return db.coinDao()
        }
    }

    @Binds
    @Singleton
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository
}
