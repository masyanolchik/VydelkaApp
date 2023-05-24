package com.hneu.vydelka.di.cart

import com.hneu.core.datasource.cart.LocalCartDataSource
import com.hneu.core.repository.cart.CartRepository
import com.hneu.core.repository.cart.CoreCartRepositoryImpl
import com.hneu.core.usecase.cart.FetchCartUseCase
import com.hneu.core.usecase.cart.SaveCartUseCase
import com.hneu.vydelka.datasource.cart.RoomCartDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.order.cart.CartDao
import com.hneu.vydelka.localdatabase.order.orderedproduct.OrderedProductDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CartModule {

    @Binds
    fun bindLocalDataSource(roomCartDataSource: RoomCartDataSource): LocalCartDataSource

    companion object {

        @Provides
        fun provideFetchCartUseCase(cartRepository: CartRepository): FetchCartUseCase {
            return FetchCartUseCase(cartRepository)
        }

        @Provides
        fun providesSaveCartUseCase(cartRepository: CartRepository): SaveCartUseCase {
            return SaveCartUseCase(cartRepository)
        }

        @Provides
        fun providesRepository(localCartDataSource: LocalCartDataSource): CartRepository {
            return CoreCartRepositoryImpl(localCartDataSource)
        }

        @Singleton
        @Provides
        fun provideOrderedProductDao(db: LocalDatabase): OrderedProductDao {
            return db.orderedProductDao()
        }

        @Singleton
        @Provides
        fun provideCartDao(db: LocalDatabase): CartDao {
            return db.cartDao()
        }
    }
}