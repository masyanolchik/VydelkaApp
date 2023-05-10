package com.hneu.vydelka.ui.feed

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.*
import com.hneu.vydelka.ui.feed.promo.PromoScreen
import com.hneu.vydelka.ui.feed.testing.MockData
import com.hneu.vydelka.ui.navigation.NavigationRoutes

@Composable
fun Feed(navController: NavHostController = rememberNavController()) {
    var openPromoScreenDialog by rememberSaveable { mutableStateOf(false) }
    var openPromoScreenTitle by rememberSaveable { mutableStateOf("") }
    if(openPromoScreenDialog) {
        PromoScreen(openPromoScreenTitle) {
           openPromoScreenDialog = !openPromoScreenDialog
        }
    } else {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            val promoImageAdapter = PromoImageAdapter() {
                openPromoScreenDialog = !openPromoScreenDialog
                openPromoScreenTitle = it.promoTitle
            }
            AndroidView(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(196.dp)
                    .padding(vertical = 8.dp),
                factory = { context -> RecyclerView(context).apply {
                    layoutManager = CarouselLayoutManager()
                    adapter = promoImageAdapter
                    clipChildren = false
                    clipToPadding = false
                } },
                update = { promoImageAdapter.submitList(MockData.imageList) },
            )
            // Placeholder for a real category links in the future
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ){
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
                SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "",) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.CategoryRoute, 0))
                }
            }
            Divider()
            // Placeholder for a real section in the future
            SectionLabel(text = stringResource(id = R.string.feed_best_section))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                MediumBoxProductCard(
                    "Product Name Pro Max 256/16 GB",
                    "9 999 ₴",
                    "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    "",
                    onCardClicked = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0)) },
                    onCartAddedClicked = {},
                )
                MediumBoxProductCard(
                    "Product Name Pro Max 256/16 GB",
                    "9 999 ₴",
                    "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    "",
                    onCardClicked = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0)) },
                    onCartAddedClicked = {},
                )
                MediumBoxProductCard(
                    "Product Name Pro Max 256/16 GB",
                    "9 999 ₴",
                    "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    "",
                    onCardClicked = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0)) },
                    onCartAddedClicked = {},
                )
                MediumBoxProductCard(
                    "Product Name Pro Max 256/16 GB",
                    "9 999 ₴",
                    "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    "",
                    onCardClicked = { navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0)) },
                    onCartAddedClicked = {},
                )
            }
            Divider()

            SectionLabel(text = stringResource(id = R.string.feed_history_section))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                SmallBoxProduct(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    contentDescription = "",
                ) {
                   navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                SmallBoxProduct(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    contentDescription = "",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                SmallBoxProduct(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    contentDescription = "",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                SmallBoxProduct(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    contentDescription = "",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                SmallBoxProduct(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                    contentDescription = "",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
            }
            Divider()
            SectionLabel(text = stringResource(id = R.string.computer_category))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                ProductCard(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    contentDescription = "",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                ProductCard(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    contentDescription = "",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
            }
            SectionLabel(text = stringResource(id = R.string.household_category))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                ProductCard(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    contentDescription = "",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
                ProductCard(
                    title = "Product Name Pro Max 256/16 GB",
                    price = "9 999 ₴",
                    contentDescription = "",
                    imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    Feed()
}