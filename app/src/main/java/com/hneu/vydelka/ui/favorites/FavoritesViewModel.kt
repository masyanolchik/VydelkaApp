package com.hneu.vydelka.ui.favorites

import androidx.lifecycle.ViewModel
import com.hneu.vydelka.accountmanager.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val accountManager: AccountManager,) : ViewModel() {
    val favoritesProducts = accountManager.getFavorites()
}