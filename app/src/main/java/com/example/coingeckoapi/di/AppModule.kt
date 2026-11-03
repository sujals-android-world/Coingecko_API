package com.example.coingeckoapi.di

import android.content.Context
import androidx.room.Room
import com.example.coingeckoapi.data.remote.CoingeckoAPIService
import com.example.coingeckoapi.data.repositoryImpl.CoinRepositoryImpl
import com.example.coingeckoapi.data.repositoryImpl.PreferenceRepoImpl
import com.example.coingeckoapi.data.repositoryImpl.TransactionRepoImpl
import com.example.coingeckoapi.data.repositoryImpl.UserRepositoryImpl
import com.example.coingeckoapi.data.roomdb.dao.CoinDao
import com.example.coingeckoapi.data.roomdb.dao.TransactionDao
import com.example.coingeckoapi.data.roomdb.dao.UserDao
import com.example.coingeckoapi.data.roomdb.db.CoinDB
import com.example.coingeckoapi.domain.repository.CoinRepository
import com.example.coingeckoapi.domain.repository.PreferenceRepository
import com.example.coingeckoapi.domain.repository.TransactionRepository
import com.example.coingeckoapi.domain.repository.UserRepository
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.CoinUseCases
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.GetAllCoin
import com.example.coingeckoapi.domain.usacases.allUserUseCases.GetUserUseCase
import com.example.coingeckoapi.domain.usacases.allCoinUseCases.RefreshCoinUseCase
import com.example.coingeckoapi.domain.usacases.allUserUseCases.AllUserUseCases
import com.example.coingeckoapi.domain.usacases.allUserUseCases.CreateUserUseCase
import com.example.coingeckoapi.domain.usacases.allUserUseCases.GetUserDetailUseCase
import com.example.coingeckoapi.domain.usacases.allUserUseCases.SaveUserUseCase
import com.example.coingeckoapi.domain.usacases.transactionUseCases.AllTransactionUseCases
import com.example.coingeckoapi.domain.usacases.transactionUseCases.GetTransactionById
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
                refreshCoins = RefreshCoinUseCase(repository)
            )
        }

        @Provides
        @Singleton
        fun provideUserUseCases(repository: UserRepository, preferenceRepository: PreferenceRepository) : AllUserUseCases {
            return AllUserUseCases(
                getUserUseCase = GetUserUseCase(preferenceRepository),
                saveUserUseCase = SaveUserUseCase(preferenceRepository),
                createUserUseCase = CreateUserUseCase(repository),
                getUserDetailUseCase = GetUserDetailUseCase(repository)
            )
        }

        @Provides
        @Singleton
        fun provideTransactionUseCases(transactionRepository: TransactionRepository) : AllTransactionUseCases {
            return AllTransactionUseCases(
                getTransactionById = GetTransactionById(transactionRepository)
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

        @Provides
        @Singleton
        fun provideUserDao(db: CoinDB) : UserDao {
            return db.userDao()
        }

        @Provides
        @Singleton
        fun provideTransactionDao(db : CoinDB) : TransactionDao{
            return db.transactionDao()
        }
    }

    @Binds
    @Singleton
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(preferenceRepoImpl: PreferenceRepoImpl) : PreferenceRepository

    @Binds
    @Singleton
    abstract fun provideTransactionRepository(transactionRepoImpl: TransactionRepoImpl) : TransactionRepository
}
