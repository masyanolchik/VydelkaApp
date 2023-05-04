package com.hneu.vydelka.ui.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hneu.core.domain.Category

@Composable
fun SubCategoryCard(title:String,subcategories: List<Category>) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {  },
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subcategories.forEach{ category ->
                    Text(
                        text = category.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth()
                            .clickable {  },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSubCategoryCard() {
    val dummyCategory = Category("SubCategory", null, listOf())
    SubCategoryCard(title = "CategoryName", subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory))
}