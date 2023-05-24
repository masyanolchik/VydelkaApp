package com.hneu.vydelka.di.product

import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.datasource.product.testing.FakeRemoteProductDataSource
import com.hneu.core.repository.product.CoreProductRepository
import com.hneu.core.repository.product.ProductRepository
import com.hneu.core.usecase.product.FetchProductsUseCase
import com.hneu.core.usecase.product.GetProductsByCategoryIdUseCase
import com.hneu.core.usecase.product.GetProductsByTagsUseCase
import com.hneu.core.usecase.product.GetTopProductsUseCase
import com.hneu.vydelka.datasource.product.RoomProductDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.product.ProductDao
import com.hneu.vydelka.localdatabase.product.additionalimage.AdditionalImageDao
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductModule {

    @Binds
    fun bindLocalDataSource(roomProductDataSource: RoomProductDataSource): LocalProductDataSource

    companion object {
        @Provides
        fun provideFetchProductsUseCase(productRepository: ProductRepository): FetchProductsUseCase {
            return FetchProductsUseCase(productRepository)
        }

        @Provides
        fun provideGetProductsByCategoryIdUseCase(productRepository: ProductRepository): GetProductsByCategoryIdUseCase {
            return GetProductsByCategoryIdUseCase(productRepository)
        }

        @Provides
        fun provideGetProductsByTagsUseCase(productRepository: ProductRepository): GetProductsByTagsUseCase {
            return GetProductsByTagsUseCase(productRepository)
        }

        @Provides
        fun provideGetTopProductsUseCase(productRepository: ProductRepository): GetTopProductsUseCase {
            return GetTopProductsUseCase(productRepository)
        }

        @Provides
        fun provideRepository(localProductDataSource: LocalProductDataSource): ProductRepository {
            return CoreProductRepository(localProductDataSource, FakeRemoteProductDataSource())
        }

        @Singleton
        @Provides
        fun provideAdditionalImageDao(db: LocalDatabase): AdditionalImageDao {
            return db.additionalImageDao()
        }

        @Singleton
        @Provides
        fun provideProductDao(db: LocalDatabase): ProductDao {
            return db.productDao()
        }

        @Singleton
        @Provides
        fun provideTagDao(db: LocalDatabase) : TagDao {
            return db.tagDao()
        }
    }
}