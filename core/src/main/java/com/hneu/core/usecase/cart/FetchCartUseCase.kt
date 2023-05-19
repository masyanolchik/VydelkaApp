package com.hneu.core.usecase.cart

import com.hneu.core.repository.cart.CartRepository

class FetchCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke() = cartRepository.fetchCart()
}