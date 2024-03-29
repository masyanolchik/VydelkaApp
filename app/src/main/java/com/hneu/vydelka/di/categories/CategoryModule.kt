package com.hneu.vydelka.di.categories

import com.hneu.core.datasource.category.LocalCategoryDataSource
import com.hneu.core.datasource.category.RemoteCategoryDataSource
import com.hneu.core.datasource.category.testing.FakeRemoteCategoryDataSource
import com.hneu.core.repository.category.CategoryRepository
import com.hneu.core.repository.category.CoreCategoryRepository
import com.hneu.core.usecase.category.FetchCategoryUseCase
import com.hneu.vydelka.datasource.category.RetrofitCategoryDataSource
import com.hneu.vydelka.datasource.category.RoomCategoryDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.product.attribute.AttributeDao
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupDao
import com.hneu.vydelka.localdatabase.product.category.CategoryDao
import com.hneu.vydelka.network.categories.CategoryApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CategoryModule {

    @Binds
    fun bindLocalDataSource(roomCategoryDataSource: RoomCategoryDataSource) : LocalCategoryDataSource

    @Binds
    fun bindRemoteDataSource(retrofitDataSource: RetrofitCategoryDataSource): RemoteCategoryDataSource
    companion object {

        @Provides
        fun provideCategoryFetchUseCase(categoryRepository: CategoryRepository): FetchCategoryUseCase {
            return FetchCategoryUseCase(categoryRepository)
        }

        @Provides
        fun provideRepository(localCategoryDataSource: LocalCategoryDataSource, remoteCategoryDataSource: RemoteCategoryDataSource) : CategoryRepository {
            return CoreCategoryRepository(localCategoryDataSource, remoteCategoryDataSource)
        }

        @Singleton
        @Provides
        fun provideAttributeDao(db: LocalDatabase) : AttributeDao {
            return db.attributeDao()
        }

        @Singleton
        @Provides
        fun provideAttributeGroupDao(db: LocalDatabase) : AttributeGroupDao {
            return db.attributeGroupDao()
        }

        @Singleton
        @Provides
        fun provideCategoryDao(db: LocalDatabase) : CategoryDao {
            return db.categoryDao()
        }

        @Singleton
        @Provides
        fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
            return retrofit.create(CategoryApiService::class.java)
        }
    }

}