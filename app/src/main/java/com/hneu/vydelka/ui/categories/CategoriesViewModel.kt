package com.hneu.vydelka.ui.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.ElectricCar
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Handyman
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Yard
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.core.usecase.category.FetchCategoryUseCase
import com.hneu.core.usecase.product.FindProductsBySearchQueryUseCase
import com.hneu.core.usecase.product.GetProductsByCategoryIdUseCase
import com.hneu.vydelka.accountmanager.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val productsByCategoryIdUseCase: GetProductsByCategoryIdUseCase,
    private val findProductsBySearchQueryUseCase: FindProductsBySearchQueryUseCase,
    private val accountManager: AccountManager,
): ViewModel() {

    val cart = accountManager.getCart()

    class CategoryNode(
        var category: Category,
        val categoryIcon: ImageVector,
        var directChildren: MutableSet<CategoryNode>,
    )

    private val _categoryTree =
        MutableStateFlow<Result<List<CategoryNode>>>(Result.Loading())
    val categoryTree : Flow<Result<List<CategoryNode>>>
        get() = _categoryList
                .map { result ->
                    when(result) {
                        is Result.Success -> {
                            val categoryNodes = result.data
                            categoryNodes.forEach {  categoryNode ->
                                categoryNode.category.parentCategory?.let { category: Category ->
                                    categoryNodes.find { category.id == it.category.id }?.directChildren?.add(categoryNode)
                                }
                            }
                            Result.Success(categoryNodes.filter { it.category.parentCategory == null })
                        }
                        is Result.Completed -> Result.Completed()
                        is Result.Error -> Result.Error(result.throwable)
                        is Result.Loading -> Result.Loading()
                    }
                }

    private val _categoryList =
        MutableStateFlow<Result<List<CategoryNode>>>(Result.Loading())
    val categoryList = _categoryList

    private val _currentCategory = MutableStateFlow<Category?>(null)
    val currentCategory = _currentCategory

    private val _productList =
        MutableStateFlow<Result<List<Product>>>(Result.Loading())
    val productStateFlow = _productList

    private val _foundProductList =
        MutableStateFlow<Result<List<Product>>>(Result.Loading())
    val foundProductListStateFlow = _foundProductList

    init {
        fetchCategoryList()
    }

    fun fetchCategoryList() {
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
                                        "Побутова техніка", "Пилососи" -> Icons.Outlined.Home
                                        "Мобільний зв'язок", "Мобільні телефони" -> Icons.Outlined.PhoneAndroid
                                        "Портативна техніка" -> Icons.Outlined.CameraAlt
                                        "Телевізори" -> Icons.Outlined.Tv
                                        "Комп'ютери та комп'ютерна техніка" -> Icons.Outlined.Computer
                                        "Авто" -> Icons.Outlined.ElectricCar
                                        "Інструменти" -> Icons.Outlined.Handyman
                                        "Сад, дача" -> Icons.Outlined.Yard
                                         else -> Icons.Outlined.Error
                                    }
                                    CategoryNode(
                                        category = it,
                                        categoryIcon = categoryIcon,
                                        directChildren = mutableSetOf(),
                                    )
                                }
                            _categoryList.emit(Result.Success(categoryNodes))
                        }
                        is Result.Completed -> _categoryTree.emit(Result.Completed())
                        is Result.Error -> _categoryTree.emit(Result.Error(result.throwable))
                        is Result.Loading -> _categoryTree.emit(Result.Loading())
                    }
                }
        }
    }

    fun performSearch(searchQuery: String) {
        viewModelScope.launch {
            findProductsBySearchQueryUseCase
                .invoke(searchQuery)
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .collectLatest {
                    _foundProductList.emit(it)
                }
        }
    }

    fun getCategoryById(id: Int): StateFlow<Category?> {
        viewModelScope.launch {
            categoryList.collectLatest { categoryResult ->
                when(categoryResult) {
                    is Result.Success -> {
                        categoryResult.data.find {
                            it.category.id == id
                        }?.let {
                            currentCategory.emit(it.category)
                        }
                    }
                    else -> {}
                }
            }
        }
        return currentCategory
    }

    fun fetchProductsByCategoryId(categoryId: Int): StateFlow<Result<List<Product>>> {
        CoroutineScope(Dispatchers.IO).launch {
            productsByCategoryIdUseCase
                .invoke(categoryId)
                .distinctUntilChanged()
                .collectLatest {
                    _productList.emit(it)
                }
        }
        return productStateFlow
    }

    fun addProductToCart(product: Product) {
        accountManager.addProductToCart(product)
    }

    fun addProductToFavorites(product: Product) {
        accountManager.addProductToFavorites(product)
    }

    fun removeProductFromCart(product: Product) {
        accountManager.removeProductFromCart(product)
    }

    fun removeProductFromFavorites(product: Product) {
        accountManager.removeProductFromFavorites(product)
    }
}