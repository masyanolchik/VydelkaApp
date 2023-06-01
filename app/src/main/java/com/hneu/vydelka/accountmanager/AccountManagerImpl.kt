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
import com.hneu.core.usecase.favorite.RemoveFromFavoritesUseCase
import com.hneu.core.usecase.order.FetchOrdersUseCase
import com.hneu.core.usecase.order.SaveOrderUseCase
import com.hneu.vydelka.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
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
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val fetchCartUseCase: FetchCartUseCase,
    private val saveCartUseCase: SaveCartUseCase,
    private val fetchOrdersUseCase: FetchOrdersUseCase,
    private val saveOrderUseCase: SaveOrderUseCase,
) : AccountManager {
    private  var _currentUser: User = UNREGISTERED_USER
    private var _cart: Cart = CART_UNREGISTERED_USER
    private var cartStateFlow: MutableStateFlow<Cart> = MutableStateFlow(CART_UNREGISTERED_USER)
    private var favoritesFlow: MutableStateFlow<Result<List<Product>>> = MutableStateFlow(Result.Loading())
    private val isUserLoggedIn = MutableStateFlow(sharedPreferences.getBoolean(SHARED_PREF_KEY, false))

    init {
        coroutineScope.launch {
            val isLogged = isUserLoggedIn.value
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
                        cartStateFlow.emit(_cart)
                    }
                    else -> {
                        _cart = CART_UNREGISTERED_USER
                        cartStateFlow.emit(_cart)
                        saveCartUseCase
                            .invoke(_cart)
                            .flowOn(Dispatchers.IO)
                            .collect()
                    }
                }
            }
        }

    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return isUserLoggedIn
    }

    override fun getCurrentUser() = _currentUser

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addProductToFavorites(product: Product): StateFlow<Result<List<Product>>> {
        coroutineScope.launch {
            addToFavoritesUseCase
                .invoke(_currentUser.id, product)
                .flowOn(Dispatchers.IO)
                .flatMapLatest {result ->
                    when(result) {
                        is Result.Success -> {
                            fetchFavoriteProductsUseCase.invoke(_currentUser.id)
                        }
                        is Result.Completed -> flowOf(Result.Completed())
                        is Result.Error -> flowOf(Result.Error(result.throwable))
                        is Result.Loading -> flowOf(Result.Loading())
                    }
                }
                .collectLatest {
                    favoritesFlow.emit(it)
                }
        }
        return favoritesFlow
    }

    override fun removeProductFromFavorites(product: Product): StateFlow<Result<List<Product>>> {
        coroutineScope.launch {
            removeFromFavoritesUseCase
                .invoke(_currentUser.id, product)
                .flowOn(Dispatchers.IO)
                .flatMapLatest {
                    fetchFavoriteProductsUseCase.invoke(_currentUser.id)
                }
                .collectLatest {
                    favoritesFlow.emit(it)
                }
        }
        return favoritesFlow
    }

    override fun getCart(): StateFlow<Cart> {
        coroutineScope.launch {
            fetchCartUseCase
                .invoke(_currentUser.id)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when(it) {
                        is Result.Success -> {
                            cartStateFlow.emit(_cart)
                        }
                        else -> {}
                    }
                }
        }
        return cartStateFlow
    }

    override fun getFavorites(): StateFlow<Result<List<Product>>> {
        coroutineScope.launch {
            fetchFavoriteProductsUseCase
                .invoke(_currentUser.id)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    favoritesFlow.emit(it)
                }
        }
        return favoritesFlow
    }

    override fun addProductToCart(product: Product): StateFlow<Cart> {
        _cart.addProductToCart(product)
        coroutineScope.launch {
            saveCartUseCase.invoke(_cart)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when(it) {
                        is Result.Success -> {
                            cartStateFlow.emit(it.data)
                        }
                        is Result.Error -> {
                        }
                        else -> {
                        }
                    }
                }
        }
        return cartStateFlow
    }

    override fun changeProductQuantityInCart(product: Product, desiredQuantity: Int): StateFlow<Cart> {
        _cart.changeProductQuantityInCart(product, desiredQuantity)
        coroutineScope.launch {
            saveCartUseCase
                .invoke(_cart)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when(it) {
                        is Result.Success -> {
                            cartStateFlow.emit(it.data)
                        }
                        else -> {}
                    }
                }
        }
        return cartStateFlow
    }

    override fun removeProductFromCart(product: Product): StateFlow<Cart> {
        _cart.deleteProductFromCart(product)
        coroutineScope.launch {
            saveCartUseCase.invoke(_cart)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when(it) {
                        is Result.Success -> {
                            cartStateFlow.emit(it.data)
                        }
                        else -> {}
                    }
                }
        }
        return cartStateFlow
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