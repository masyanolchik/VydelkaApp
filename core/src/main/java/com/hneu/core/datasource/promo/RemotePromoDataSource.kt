package com.hneu.core.datasource.promo

import com.hneu.core.domain.promo.Promo
import kotlinx.coroutines.flow.Flow

interface RemotePromoDataSource {
    fun getPromos(): Flow<Result<List<Promo>>>
}