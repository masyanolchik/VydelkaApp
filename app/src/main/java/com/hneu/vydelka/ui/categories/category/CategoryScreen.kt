package com.hneu.vydelka.ui.categories.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hneu.vydelka.ui.navigation.CategoryTopAppBar

@Composable
fun CategoryScreen(navController: NavController, id:Int) {
    Scaffold(
        topBar = {
            CategoryTopAppBar(
                title = "Category name",
                onClose = { navController.popBackStack() }
            )
        }
    ) {
        Text(
            modifier = Modifier.padding(it),
            text = id.toString(),
        )
    }
}