package com.hneu.core.repository.promo

import com.hneu.core.domain.promo.Promo
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface PromoRepository {
    fun fetchPromos(): Flow<Result<List<Promo>>>
}