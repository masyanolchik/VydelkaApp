package com.hneu.vydelka.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Product
import com.hneu.vydelka.accountmanager.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import  com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val accountManager: AccountManager,) : ViewModel() {
    val _favoriteProducts: MutableStateFlow<Result<List<Product>>> = MutableStateFlow(Result.Loading())
    val favoritesProducts: StateFlow<Result<List<Product>>> = _favoriteProducts
    val userCart = accountManager.getCart()

    init {
        fetchFavorites()
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            accountManager.getFavorites()
                .collectLatest {
                    _favoriteProducts.emit(it)
                }
        }
    }
}