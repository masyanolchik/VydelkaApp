package com.hneu.vydelka.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.hneu.core.domain.request.Result
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.product.Category
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.categories.components.MediumCategoryCard
import com.hneu.vydelka.ui.categories.components.SubCategoryCard
import com.hneu.vydelka.ui.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    navController: NavHostController = rememberNavController(),
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
) {
    val categoryNodes by categoriesViewModel.categoryTree.collectAsState(initial = Result.Loading())
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            when(categoryNodes) {
                is Result.Success -> {
                    var selectedRootCategoryId by rememberSaveable {
                        mutableStateOf((categoryNodes as Result.Success<List<CategoriesViewModel.CategoryNode>>)
                            .data.first().category.id) }
                    CategorySelectionStrip(
                        selectedCategoryId = selectedRootCategoryId,
                        rootCategoryList = (categoryNodes as Result.Success<List<CategoriesViewModel.CategoryNode>>).data
                    ) { selectedId: Int ->
                        selectedRootCategoryId = selectedId
                    }
                    SubCategorySelectionStrip(
                        childrenNodes = (categoryNodes as Result.Success<List<CategoriesViewModel.CategoryNode>>)
                            .data.first { it.category.id == selectedRootCategoryId }
                            .directChildren.toList(),
                        navController = navController,
                    )
                }
                is Result.Loading -> CircularProgressIndicator()
                else -> Text(stringResource(id = R.string.category_error_text))
            }
        }
    }
}

@Composable
fun SubCategorySelectionStrip(
    navController: NavHostController = rememberNavController(),
    childrenNodes: List<CategoriesViewModel.CategoryNode> = emptyList(),
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(childrenNodes) {
            val onCategoryClick: (Int) -> Unit = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0)) }
            SubCategoryCard(
                mainCategory = it.category,
                subcategories = it.directChildren.map { it.category },
                onCategoryClick = onCategoryClick,
            )
        }
    }
}

@Composable
fun CategorySelectionStrip(
    selectedCategoryId: Int = -1,
    rootCategoryList: List<CategoriesViewModel.CategoryNode> = emptyList(),
    onClick: (Int) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(rootCategoryList) {
            MediumCategoryCard(
                title = it.category.name,
                imageVector = it.categoryIcon,
                contentDescription = it.category.name,
                selected = it.category.id == selectedCategoryId,
                onClick = {
                    onClick.invoke(it.category.id)
                }
            )
        }
    }
}