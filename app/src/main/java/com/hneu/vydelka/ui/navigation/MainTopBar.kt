package com.hneu.vydelka.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hneu.vydelka.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(showLogo: Boolean = true, showSearch: Boolean = false, onCartButtonClick: () -> Unit,) {
    var text by rememberSaveable { mutableStateOf("") }
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            if(showLogo) {
                Image(
                    painter = painterResource(id = R.drawable.logo_top_app_bar),
                    contentDescription = stringResource(id = R.string.top_app_bar_logo_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(width = 600.dp,height = 42.dp),
                )
            }
        },
        actions = {
            if(showSearch) {
                ConstraintLayout(modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth()) {
                    val (searchBarRef, cartRef) = createRefs()
                    SearchBar(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {  },
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text(stringResource(R.string.category_search_hint)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                            .constrainAs(searchBarRef) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top, 4.dp)
                                bottom.linkTo(parent.bottom, 4.dp)
                                end.linkTo(cartRef.start)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                    ) {

                    }
                    Box(
                        modifier = Modifier.constrainAs(cartRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                    ) {
                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            val badgeNumber = "3"
                            Text(
                                badgeNumber,
                                modifier = Modifier.semantics {
                                    contentDescription = "$badgeNumber new notifications"
                                }
                            )
                        }
                        IconButton(onClick = onCartButtonClick) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = stringResource(id = R.string.top_app_bar_cart_icon_description),
                            )
                        }
                    }
                }
            } else {
                Box {
                    Badge(
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        val badgeNumber = "3"
                        Text(
                            badgeNumber,
                            modifier = Modifier.semantics {
                                contentDescription = "$badgeNumber new notifications"
                            }
                        )
                    }
                    IconButton(onClick = onCartButtonClick) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = stringResource(id = R.string.top_app_bar_cart_icon_description),
                        )
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTopAppBar(
    title: String = "",
    onClose: () -> Unit = {},
    onSortButtonClick: () -> Unit = {},
    onFilterButtonClick: () -> Unit = {},
    onCartButtonClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description)
            )
        }
    }
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = topAppBarNavigationIcon,
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = onSortButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = stringResource(id = R.string.catalogue_sort_button_description)
                )
            }
            IconButton(onClick = onFilterButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = stringResource(id = R.string.catalogue_filter_button_description)
                )
            }
            Box {
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    val badgeNumber = "3"
                    Text(
                        badgeNumber,
                        modifier = Modifier.semantics {
                            contentDescription = "$badgeNumber new notifications"
                        }
                    )
                }
                IconButton(onClick = onCartButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = stringResource(id = R.string.top_app_bar_cart_icon_description),
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    MainTopBar {}
}

@Preview(showBackground = true)
@Composable
fun CategoryTopAppBar() {
    CategoryTopAppBar(
        "CATEGORY NAME",
        {

        },
    )
}