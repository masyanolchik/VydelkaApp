package com.hneu.core.usecase.cart

import com.hneu.core.domain.order.Cart
import com.hneu.core.repository.cart.CartRepository

class SaveCartUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(cart: Cart) = cartRepository.saveCart(cart)
}