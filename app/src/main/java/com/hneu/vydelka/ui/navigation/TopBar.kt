package com.hneu.vydelka.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hneu.vydelka.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo_top_app_bar),
                contentDescription = stringResource(id = R.string.top_app_bar_logo_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 600.dp,height = 42.dp),
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = stringResource(id = R.string.top_app_bar_cart_icon_description),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}
