package com.hneu.vydelka.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.product.Category
import com.hneu.vydelka.ui.categories.components.MediumCategoryCard
import com.hneu.vydelka.ui.categories.components.SubCategoryCard
import com.hneu.vydelka.ui.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(navController: NavHostController = rememberNavController()) {
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
            CategorySelectionStrip()
            SubCategorySelectionStrip(navController)
        }
    }
}

@Composable
fun SubCategorySelectionStrip(navController: NavHostController = rememberNavController()) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val mainCategory = Category("MainCategory", null, listOf())
        val dummyCategory = Category("SubCategory", mainCategory, listOf())
        val onCategoryClick: (Int) -> Unit = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0)) }
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
        SubCategoryCard(mainCategory = mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory), onCategoryClick)
    }
}

@Composable
fun CategorySelectionStrip() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = "",
            selected = true,
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = "",
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
        MediumCategoryCard(
            title = "category name",
            imageVector = Icons.Outlined.PhoneIphone,
            contentDescription = ""
        )
    }
}