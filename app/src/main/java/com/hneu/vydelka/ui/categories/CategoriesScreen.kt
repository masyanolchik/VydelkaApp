package com.hneu.vydelka.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hneu.core.domain.Category
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.categories.components.MediumCategoryCard
import com.hneu.vydelka.ui.categories.components.SubCategoryCard

@Composable
fun Categories() {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
        Text(
            text = stringResource(id = R.string.bottom_bar_catalogue)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            CategorySelectionStrip()
            SubCategorySelectionStrip()
        }
    }
}

@Composable
fun SubCategorySelectionStrip() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dummyCategory = Category("SubCategory", null, listOf())
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
        SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
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