package com.hneu.vydelka.di.order

import com.hneu.core.datasource.order.LocalOrderDataSource
import com.hneu.core.datasource.order.RemoteOrderDataSource
import com.hneu.core.datasource.order.testing.FakeRemoteOrderDataSource
import com.hneu.core.repository.order.CoreOrderRepository
import com.hneu.core.repository.order.OrderRepository
import com.hneu.core.usecase.order.FetchOrdersUseCase
import com.hneu.core.usecase.order.SaveOrderUseCase
import com.hneu.vydelka.datasource.order.RetrofitOrderDataSource
import com.hneu.vydelka.datasource.order.RoomOrderDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.order.OrderDao
import com.hneu.vydelka.network.order.OrderApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface OrderModule {

    @Binds
    fun bindLocalDataSource(roomOrderDataSource: RoomOrderDataSource): LocalOrderDataSource

    @Binds
    fun bindRemoteLocalDataSource(retrofitOrderDataSource: RetrofitOrderDataSource): RemoteOrderDataSource

    companion object {

        @Provides
        fun provideSaveOrderUseCase(orderRepository: OrderRepository): SaveOrderUseCase {
            return SaveOrderUseCase(orderRepository)
        }

        @Provides
        fun provideFetchOrdersUseCase(orderRepository: OrderRepository): FetchOrdersUseCase {
            return FetchOrdersUseCase(orderRepository)
        }

        @Provides
        fun provideRepository(localOrderDataSource: LocalOrderDataSource, remoteOrderDataSource: RemoteOrderDataSource): OrderRepository {
            return CoreOrderRepository(localOrderDataSource, remoteOrderDataSource)
        }

        @Singleton
        @Provides
        fun provideOrderDao(db: LocalDatabase): OrderDao {
            return db.orderDao()
        }

        @Singleton
        @Provides
        fun provideApi(retrofit: Retrofit): OrderApiService {
            return retrofit.create(OrderApiService::class.java)
        }
    }
}