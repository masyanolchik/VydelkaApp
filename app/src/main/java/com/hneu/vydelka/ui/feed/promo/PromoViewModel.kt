package com.hneu.vydelka.ui.feed.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.promo.Promo
import com.hneu.core.usecase.promo.FetchPromosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

@HiltViewModel
class PromoViewModel @Inject constructor(private val fetchPromosUseCase: FetchPromosUseCase): ViewModel() {

    private val _promoFlow = MutableStateFlow<Result<Promo>>(Result.Loading())
    val promoFlow: StateFlow<Result<Promo>> = _promoFlow

    fun getPromoById(id: Int) {
        viewModelScope.launch {
            fetchPromosUseCase
                .invoke()
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    if (it is Result.Success) {
                        val product = it.data.find { it.id == id }
                        if(product != null) {
                            _promoFlow.emit(Result.Success(product))
                        } else {
                            _promoFlow.emit(Result.Completed())
                        }
                    }
                }
        }
    }

}