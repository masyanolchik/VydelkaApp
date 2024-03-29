package com.hneu.core.datasource.promo

import com.hneu.core.domain.promo.Promo
import kotlinx.coroutines.flow.Flow

import com.hneu.core.domain.request.Result

interface LocalPromoDataSource {
    fun savePromos(promos: List<Promo>): Flow<Result<List<Promo>>>
    fun getPromos(): Flow<Result<List<Promo>>>
}