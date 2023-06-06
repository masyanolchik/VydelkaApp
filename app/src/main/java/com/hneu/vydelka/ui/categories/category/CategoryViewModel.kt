package com.hneu.vydelka.ui.categories.category

import androidx.lifecycle.ViewModel
import com.hneu.core.domain.product.FilterModel
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.product.FetchSortedProductsByCategoryIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val fetchSortedProductsByCategoryIdUseCase: FetchSortedProductsByCategoryIdUseCase,
) : ViewModel() {
    private val _productList =
        MutableStateFlow<Result<List<Product>>>(Result.Loading())
    val productStateFlow: StateFlow<Result<List<Product>>> = _productList

    private val _minBoundPrice = MutableStateFlow(BigDecimal(0))
    val minBoundPrice: StateFlow<BigDecimal> = _minBoundPrice

    private val _maxBoundPrice = MutableStateFlow(BigDecimal(0))
    val maxBoundPrice: StateFlow<BigDecimal> = _maxBoundPrice

    private val _bottomSheetState = MutableStateFlow(CategoryScreenBottomSheetState.COLLAPSED)
    val bottomSheetState: StateFlow<CategoryScreenBottomSheetState> = _bottomSheetState

    private val _currentSortingMethod = MutableStateFlow(SortingMethods.LOW_PRICE)
    val currentSortingMethod: StateFlow<SortingMethods> = _currentSortingMethod

    private val _currentAppliedFilter = MutableStateFlow<FilterModel?>(null)
    val currentAppliedFilter: StateFlow<FilterModel?> = _currentAppliedFilter

    enum class CategoryScreenBottomSheetState{
        COLLAPSED,
        SORTING,
        FILTER,
        CART,
    }

    enum class SortingMethods {
        LOW_PRICE,
        HIGH_PRICE,
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchSortedProductsByCategoryId(
        categoryId: Int,
        sortingMethod: SortingMethods = SortingMethods.LOW_PRICE,
        filterModel: FilterModel? = null
    ): StateFlow<Result<List<Product>>> {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSortedProductsByCategoryIdUseCase
                .invoke(sortingMethod.ordinal,categoryId)
                .flatMapLatest {
                    when(it) {
                        is Result.Success -> {
                            if(it.data.isNotEmpty()) {
                                _minBoundPrice.emit((it.data.minBy { it.price }.price))
                                _maxBoundPrice.emit(it.data.maxBy { it.price }.price)
                            }
                            if(filterModel != null) {
                                val ks = it.data.filter { it.matchesFilterCriteria(filterModel) }
                                flowOf(Result.Success(ks))
                            } else {
                                flowOf(it)
                            }
                        }
                        else -> flowOf(it)
                    }
                }
                .collectLatest {
                    _currentAppliedFilter.emit(filterModel)
                    _currentSortingMethod.emit(sortingMethod)
                    _productList.emit(it)
                }
        }
        return productStateFlow
    }



    fun changeBottomSheetState(categoryScreenBottomSheetState: CategoryScreenBottomSheetState) {
        _bottomSheetState.value = categoryScreenBottomSheetState
    }
}