package com.hneu.vydelka.ui.feed.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.hneu.vydelka.R

@Composable
fun ProductCard(
    title: String,
    price: String,
    contentDescription: String,
    imageSrc: String,
) {
    var isProductAddedToCart by rememberSaveable { mutableStateOf(false) }
    var isProductFavorited by rememberSaveable { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .height(150.dp)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min),
                    verticalArrangement = Arrangement.Center,
                ) {
                    AsyncImage(
                        model = imageSrc,
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .fillMaxWidth()
                            .clip(ShapeDefaults.Small),
                    )
                }
                ConstraintLayout(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)) {
                    val (titleRef, priceRef, actionRowRef) = createRefs()
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = title,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(titleRef) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(priceRef.top)
                                height = Dimension.fillToConstraints
                            }
                    )
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = price,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(priceRef) {
                                bottom.linkTo(actionRowRef.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(actionRowRef) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        horizontalArrangement = Arrangement.End,
                    ) {
                        val cartIcon = if(isProductAddedToCart) {
                            Icons.Filled.ShoppingCart
                        } else {
                            Icons.Outlined.AddShoppingCart
                        }
                        val favoritesIcon = if(isProductFavorited) {
                            Icons.Outlined.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        }
                        FilledIconButton(
                            onClick = { isProductFavorited = !isProductFavorited },
                        ) {
                            Icon(
                                imageVector = favoritesIcon,
                                contentDescription = stringResource(id = R.string.add_to_favorites_button_label)
                            )
                        }
                        FilledIconButton(
                            onClick = { isProductAddedToCart = !isProductAddedToCart },
                        ) {
                            Icon(
                                imageVector = cartIcon,
                                contentDescription = stringResource(id = R.string.add_to_cart_button_label)
                            )
                        }
                    }
                }
            }
            Divider()
        }
    }
}

@Preview
@Composable
fun PreviewProductCard() {
    ProductCard(
        title = "Product Name Pro Max 256/16 GB",
        price = "9 999 ₴",
        contentDescription = "",
        imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
    )
}