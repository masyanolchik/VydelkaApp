package com.hneu.vydelka.di.favorite

import com.hneu.core.datasource.favorite.LocalFavoriteDataSource
import com.hneu.core.repository.favorite.CoreFavoriteRepository
import com.hneu.core.repository.favorite.FavoriteRepository
import com.hneu.core.usecase.favorite.AddToFavoritesUseCase
import com.hneu.core.usecase.favorite.FetchFavoriteProductsUseCase
import com.hneu.vydelka.datasource.favorite.RoomFavoriteDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FavoriteModule {

    @Binds
    fun bindLocalDataSource(roomFavoriteDataSource: RoomFavoriteDataSource): LocalFavoriteDataSource

    companion object {

        @Provides
        fun provideAddToFavoritesUseCase(favoriteRepository: FavoriteRepository): AddToFavoritesUseCase {
            return AddToFavoritesUseCase(favoriteRepository)
        }

        @Provides
        fun provideFetchFavoritesUseCase(favoriteRepository: FavoriteRepository): FetchFavoriteProductsUseCase {
            return FetchFavoriteProductsUseCase(favoriteRepository)
        }

        @Provides
        fun providesRepository(localFavoriteDataSource: LocalFavoriteDataSource): FavoriteRepository {
            return CoreFavoriteRepository(localFavoriteDataSource)
        }
    }
}