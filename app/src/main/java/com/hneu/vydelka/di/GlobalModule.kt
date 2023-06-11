package com.hneu.vydelka.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.hneu.vydelka.VydelkaApplication
import com.hneu.vydelka.accountmanager.AccountManager
import com.hneu.vydelka.accountmanager.AccountManagerImpl
import com.hneu.vydelka.di.coroutinesscopes.DefaultDispatcher
import com.hneu.vydelka.di.coroutinesscopes.IoDispatcher
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.network.CookieInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
interface GlobalModule {

   @Binds
   fun bindAccountManager(accountManagerImpl: AccountManagerImpl): AccountManager

    companion object {
        private const val PREFERENCES_KEY = "VYDELKA_APP_PREF"
        private const val API_URL = "http://192.168.31.114:8080/api/"

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(CookieInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideApplication(@ApplicationContext appContext: Context): VydelkaApplication {
            return appContext as VydelkaApplication
        }

        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext appContext: Context): LocalDatabase {
            return Room
                .databaseBuilder(appContext, LocalDatabase::class.java, LocalDatabase.DB_NAME)
                .build()
        }

        @Provides
        fun provideSharedPref(@ApplicationContext appContext: Context) : SharedPreferences {
            return appContext.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        }

        @Singleton
        @ApplicationScope
        @Provides
        fun provideCoroutineScope(@DefaultDispatcher defaultDispatcher: CoroutineDispatcher): CoroutineScope {
            return CoroutineScope(SupervisorJob() + defaultDispatcher)
        }
    }
}