package com.hneu.vydelka.di.promo

import com.hneu.core.datasource.promo.LocalPromoDataSource
import com.hneu.core.datasource.promo.testing.FakeRemotePromoDataSource
import com.hneu.core.repository.promo.CorePromoRepository
import com.hneu.core.repository.promo.PromoRepository
import com.hneu.core.usecase.promo.FetchPromosUseCase
import com.hneu.vydelka.datasource.promo.RoomPromoDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.promo.PromoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PromoModule {
    @Binds
    fun provideLocalDataSource(roomDataSource: RoomPromoDataSource) : LocalPromoDataSource

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
        fun providePromoRepository(localDataSource: LocalPromoDataSource): PromoRepository {
            return CorePromoRepository(localDataSource, FakeRemotePromoDataSource())
        }

    }
}