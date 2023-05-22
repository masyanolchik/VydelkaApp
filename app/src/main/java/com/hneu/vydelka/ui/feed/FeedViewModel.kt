package com.hneu.vydelka.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.promo.Promo
import com.hneu.core.repository.promo.PromoRepository
import com.hneu.core.domain.request.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val promoRepository: PromoRepository
) : ViewModel() {
    private var _promosStateFlow : MutableStateFlow<Result<List<Promo>>> =
        MutableStateFlow(Result.Loading())
    val promosStateFlow = _promosStateFlow

    init {
        fetchPromos()
    }

    fun fetchPromos() {
        viewModelScope.launch {
            promoRepository
                .fetchPromos()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _promosStateFlow.emit(it)
                }
        }
    }
}