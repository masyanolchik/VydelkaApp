package com.hneu.vydelka.accountmanager

import android.content.SharedPreferences
import com.hneu.core.domain.order.Cart
import com.hneu.core.domain.order.Order
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.user.User
import com.hneu.core.repository.user.UserRepository
import javax.inject.Singleton
import com.hneu.core.domain.request.Result
import com.hneu.core.repository.cart.CartRepository
import com.hneu.core.repository.favorite.FavoriteRepository
import com.hneu.core.usecase.cart.FetchCartUseCase
import com.hneu.core.usecase.cart.SaveCartUseCase
import com.hneu.core.usecase.favorite.AddToFavoritesUseCase
import com.hneu.core.usecase.favorite.FetchFavoriteProductsUseCase
import com.hneu.core.usecase.order.FetchOrdersUseCase
import com.hneu.core.usecase.order.SaveOrderUseCase
import com.hneu.vydelka.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Singleton
class AccountManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationScope private val coroutineScope: CoroutineScope,
    private val userRepository: UserRepository,
    private val fetchFavoriteProductsUseCase: FetchFavoriteProductsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val fetchCartUseCase: FetchCartUseCase,
    private val saveCartUseCase: SaveCartUseCase,
    private val fetchOrdersUseCase: FetchOrdersUseCase,
    private val saveOrderUseCase: SaveOrderUseCase,
) : AccountManager {
    private lateinit var _currentUser: User
    private lateinit var _cart: Cart

    init {
        coroutineScope.launch {
            val isLogged = sharedPreferences.getBoolean(SHARED_PREF_KEY, false)
            if(!isLogged) {
                val userResult = userRepository
                    .getByUsernameAndPassword(UNREGISTERED_USER.username, UNREGISTERED_USER.password)
                    .flowOn(Dispatchers.IO)
                    .first()

                when(userResult) {
                    is Result.Success -> {
                        _currentUser = userResult.data
                    }
                    else -> {
                        _currentUser = UNREGISTERED_USER
                        userRepository
                            .saveUser(_currentUser)
                            .flowOn(Dispatchers.IO)
                            .collect()
                    }
                }
               val cartResult =
                   fetchCartUseCase
                       .invoke(_currentUser.id)
                       .flowOn(Dispatchers.IO)
                       .first()

                when(cartResult) {
                    is Result.Success -> {
                        _cart = cartResult.data
                    }
                    else -> {
                        _cart = CART_UNREGISTERED_USER
                        saveCartUseCase
                            .invoke(_cart)
                            .flowOn(Dispatchers.IO)
                            .collect()
                    }
                }
            }

        }

    }

    override fun getCurrentUser() = _currentUser

    override fun addProductToFavorites(product: Product): Flow<Result<Product>> {
        return addToFavoritesUseCase.invoke(_currentUser.id, product)
    }

    override fun getCart(): Flow<Result<Cart>> {
        return flowOf(Result.Success(_cart))
    }

    override fun getFavorites(): Flow<Result<List<Product>>> {
        return fetchFavoriteProductsUseCase.invoke(_currentUser.id)
    }

    override fun addProductToCart(product: Product): Flow<Result<Cart>> {
        _cart.addProductToCart(product)
        return saveCartUseCase.invoke(_cart)
    }

    override fun changeProductQuantityInCart(product: Product, desiredQuantity: Int): Flow<Result<Cart>> {
        _cart.changeProductQuantityInCart(product, desiredQuantity)
        return saveCartUseCase.invoke(_cart)
    }

    override fun removeProductFromCart(product: Product): Flow<Result<Cart>> {
        _cart.deleteProductFromCart(product)
        return saveCartUseCase.invoke(_cart)
    }

    override fun getOrders(): Flow<Result<List<Order>>> {
        return fetchOrdersUseCase.invoke(_currentUser)
    }

    override fun saveOrder(order: Order): Flow<Result<Order>> {
        return saveOrderUseCase.invoke(order, _currentUser)
    }

    companion object {
        private const val SHARED_PREF_KEY = "isLogged"
        val UNREGISTERED_USER =
            User(
                id = 0,
                username = "UNREGISTERED",
                name = "UNREGISTERED",
                lastname = "UNREGISTERED",
                password = "nopass",
                phoneNumber = "",
                address = "",
                orderHistory = emptyList(),
                productHistory = emptyList(),
                favoriteProducts = emptyList(),
            )
        val CART_UNREGISTERED_USER =
            Cart(
                id = 0,
                optionalUserId = UNREGISTERED_USER.id,
                orderedProducts = mutableListOf(),
            )
    }
}