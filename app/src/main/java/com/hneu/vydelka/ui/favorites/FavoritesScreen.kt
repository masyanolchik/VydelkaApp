package com.hneu.vydelka.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.ProductCard
import com.hneu.vydelka.ui.navigation.NavigationRoutes

@Composable
fun Favorites(navController: NavHostController = rememberNavController()) {
    val fakeProducts = listOf(
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        ),
        DummyProduct(
            name = "Product Name Pro Max 256/16 GB",
            price = "9 999 ₴",
            imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            contentDescription = "",
        )
    )
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ){
            val (sumTextRef, buttonRef) = createRefs()
            Text(
                text = stringResource(id = R.string.favorites_total_sum, 99999),
                modifier = Modifier
                    .constrainAs(sumTextRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(buttonRef.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            )
                Button(
                    onClick = { },
                    modifier = Modifier
                        .constrainAs(buttonRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart_button_label),
                    )
                }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fakeProducts) {
                ProductCard(
                    title = it.name,
                    price = it.price,
                    contentDescription = "",
                    imageSrc = it.imageSrc,
                ) {
                    navController.navigate(NavigationRoutes.getNavigationRoute(NavigationRoutes.ProductRoute, 0))
                }
            }
        }
    }
}

data class DummyProduct(
    val name: String,
    val price: String,
    val imageSrc: String,
    val contentDescription: String,
)

@Preview(showBackground = true)
@Composable
fun FavoritesPreview() {
    Favorites()
}