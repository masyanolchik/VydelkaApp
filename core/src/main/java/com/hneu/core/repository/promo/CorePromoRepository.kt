package com.hneu.core.repository.promo

import com.hneu.core.datasource.promo.LocalPromoDataSource
import com.hneu.core.datasource.promo.RemotePromoDataSource
import com.hneu.core.domain.promo.Promo
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
class CorePromoRepository(
    private val localDataSource: LocalPromoDataSource,
    private val remoteDataSource: RemotePromoDataSource
) : PromoRepository {
    override fun fetchPromos(): Flow<Result<List<Promo>>> {
        return remoteDataSource
            .getPromos()
            .flatMapLatest { result->
                when(result) {
                    is Result.Success -> {
                        localDataSource.savePromos(result.data)
                    }
                    else -> {
                        flowOf(result)
                    }
                }
            }
    }

}