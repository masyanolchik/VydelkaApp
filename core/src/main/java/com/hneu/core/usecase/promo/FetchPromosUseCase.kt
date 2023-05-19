package com.hneu.core.usecase.promo

import com.hneu.core.repository.promo.PromoRepository

class FetchPromosUseCase(private val promoRepository: PromoRepository) {
    operator fun invoke() = promoRepository.fetchPromos()
}