package com.hneu.vydelka.ui.product

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.navigation.CategoryTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, id:Int) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description)
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = topAppBarNavigationIcon,
                title = {},
            )
        }
    ) {
        Text(
            modifier = Modifier.padding(it),
            text = id.toString(),
        )
    }
}