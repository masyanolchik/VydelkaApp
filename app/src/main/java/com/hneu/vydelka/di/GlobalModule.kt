package com.hneu.vydelka.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.hneu.vydelka.di.coroutinesscopes.DefaultDispatcher
import com.hneu.vydelka.localdatabase.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
class GlobalModule {
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

    companion object {
        private const val PREFERENCES_KEY = "VYDELKA_APP_PREF"
    }
}