package com.hneu.vydelka.di.product

import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.datasource.product.testing.FakeRemoteProductDataSource
import com.hneu.core.repository.product.CoreProductRepository
import com.hneu.core.repository.product.ProductRepository
import com.hneu.core.usecase.product.FetchProductsUseCase
import com.hneu.core.usecase.product.FetchSortedProductsByCategoryIdUseCase
import com.hneu.core.usecase.product.FindProductsBySearchQueryUseCase
import com.hneu.core.usecase.product.GetProductByIdUseCase
import com.hneu.core.usecase.product.GetProductsByCategoryIdUseCase
import com.hneu.core.usecase.product.GetProductsByTagsUseCase
import com.hneu.core.usecase.product.GetTopProductsUseCase
import com.hneu.vydelka.datasource.product.RetrofitProductDataSource
import com.hneu.vydelka.datasource.product.RoomProductDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.product.ProductDao
import com.hneu.vydelka.localdatabase.product.additionalimage.AdditionalImageDao
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import com.hneu.vydelka.network.product.ProductApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductModule {

    @Binds
    fun bindLocalDataSource(roomProductDataSource: RoomProductDataSource): LocalProductDataSource

    @Binds
    fun bindRemoteDataSource(retrofitProductDataSource: RetrofitProductDataSource): RemoteProductDataSource

    companion object {
        @Provides
        fun provideFindProductsBySearchQueryUseCase(productRepository: ProductRepository) : FindProductsBySearchQueryUseCase {
            return FindProductsBySearchQueryUseCase(productRepository)
        }

        @Provides
        fun provideFetchSortedProductsWithCategory(productRepository: ProductRepository): FetchSortedProductsByCategoryIdUseCase {
            return FetchSortedProductsByCategoryIdUseCase(productRepository)
        }

        @Provides
        fun provideGetProductByIdUseCase(productRepository: ProductRepository): GetProductByIdUseCase {
            return GetProductByIdUseCase(productRepository)
        }

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
        fun provideRepository(localProductDataSource: LocalProductDataSource, remoteProductDataSource: RemoteProductDataSource): ProductRepository {
            return CoreProductRepository(localProductDataSource, remoteProductDataSource)
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
        fun provideProductApi(retrofit: Retrofit): ProductApiService {
            return retrofit.create(ProductApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideTagDao(db: LocalDatabase) : TagDao {
            return db.tagDao()
        }
    }
}