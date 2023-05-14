package com.hneu.core.datasource.promo

import com.hneu.core.domain.promo.Promo
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result


interface RemotePromoDataSource {
    fun getPromos(): Flow<Result>
}