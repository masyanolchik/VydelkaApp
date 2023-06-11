package com.hneu.vydelka.di.promo

import com.hneu.core.datasource.promo.LocalPromoDataSource
import com.hneu.core.datasource.promo.RemotePromoDataSource
import com.hneu.core.datasource.promo.testing.FakeRemotePromoDataSource
import com.hneu.core.repository.promo.CorePromoRepository
import com.hneu.core.repository.promo.PromoRepository
import com.hneu.core.usecase.promo.FetchPromosUseCase
import com.hneu.vydelka.datasource.promo.RetrofitPromoDataSource
import com.hneu.vydelka.datasource.promo.RoomPromoDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.promo.PromoDao
import com.hneu.vydelka.network.profile.AuthApiService
import com.hneu.vydelka.network.promo.PromoApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PromoModule {
    @Binds
    fun provideLocalDataSource(roomDataSource: RoomPromoDataSource) : LocalPromoDataSource

    @Binds
    fun provideRemoteDataSource(retrofitPromoDataSource: RetrofitPromoDataSource): RemotePromoDataSource
    companion object {

        @Provides
        fun provideFetchPromosUseCase(promoRepository: PromoRepository): FetchPromosUseCase {
            return FetchPromosUseCase(promoRepository)
        }

        @Singleton
        @Provides
        fun providePromoDao(db: LocalDatabase): PromoDao {
            return db.promoDao()
        }

        @Provides
        fun providePromoApiService(retrofit: Retrofit): PromoApiService {
            return retrofit.create(PromoApiService::class.java)
        }

        @Provides
        fun providePromoRepository(localDataSource: LocalPromoDataSource, remotePromoDataSource: RemotePromoDataSource): PromoRepository {
            return CorePromoRepository(localDataSource, remotePromoDataSource)
        }

    }
}