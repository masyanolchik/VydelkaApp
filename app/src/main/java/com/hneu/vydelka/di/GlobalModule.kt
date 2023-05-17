package com.hneu.vydelka.di

import android.content.Context
import androidx.room.Room
import com.hneu.vydelka.localdatabase.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}