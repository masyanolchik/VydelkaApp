package com.hneu.vydelka.ui.categories.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun SubCategoryCard(mainCategory:Category, subcategories: List<Category>, onCategoryClick: (Int) -> Unit) {
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
                text = mainCategory.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                       onCategoryClick(mainCategory.id)
                    },
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
                            .clickable {
                                onCategoryClick(category.id)
                            },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSubCategoryCard() {
    val mainCategory = Category("MainCategory", null, listOf())
    val dummyCategory = Category("SubCategory", mainCategory, listOf())
    SubCategoryCard(mainCategory =  mainCategory, subcategories = listOf(dummyCategory, dummyCategory, dummyCategory, dummyCategory)) {}
}