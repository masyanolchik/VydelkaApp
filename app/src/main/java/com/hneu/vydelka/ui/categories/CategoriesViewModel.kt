package com.hneu.vydelka.ui.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HomeMax
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.promo.Promo
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.category.FetchCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    val fetchCategoryUseCase: FetchCategoryUseCase,
): ViewModel() {

    class CategoryNode(
        var category: Category,
        val categoryIcon: ImageVector,
        var directChildren: MutableSet<CategoryNode>,
    )

    private val _categoryTree =
        MutableStateFlow<Result<List<CategoryNode>>>(Result.Loading())
    val categoryTree = _categoryTree

    init {
        fetchCategoryTree()
    }

    fun fetchCategoryTree() {
        viewModelScope.launch {
            fetchCategoryUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest { result ->
                    when(result) {
                        is Result.Success -> {
                            val categoryNodes = result.data
                                .map {
                                    val categoryIcon = when(it.name) {
                                        "Побутова техніка" -> Icons.Outlined.Home
                                        "Мобільний зв'язок" -> Icons.Outlined.PhoneAndroid
                                         else -> Icons.Outlined.Error
                                    }
                                    CategoryNode(
                                        category = it,
                                        categoryIcon = categoryIcon,
                                        directChildren = mutableSetOf(),
                                    )
                                }
                            categoryNodes.forEach {  categoryNode ->
                                categoryNode.category.parentCategory?.let { category: Category ->
                                    categoryNodes.find { category.id == it.category.id }?.directChildren?.add(categoryNode)
                                }
                            }
                            val filteredNodes = Result.Success(categoryNodes.filter { it.category.parentCategory == null })
                            _categoryTree.emit(filteredNodes)
                        }
                        is Result.Completed -> _categoryTree.emit(Result.Completed())
                        is Result.Error -> _categoryTree.emit(Result.Error(result.throwable))
                        is Result.Loading -> _categoryTree.emit(Result.Loading())
                    }
                }
        }
    }
}