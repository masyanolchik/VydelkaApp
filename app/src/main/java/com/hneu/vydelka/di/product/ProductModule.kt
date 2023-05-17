package com.hneu.vydelka.di.product

import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {
    @Singleton
    @Provides
    fun provideTagDao(db: LocalDatabase) : TagDao {
        return db.tagDao()
    }
}