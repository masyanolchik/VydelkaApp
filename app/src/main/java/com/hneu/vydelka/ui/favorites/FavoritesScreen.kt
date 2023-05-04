package com.hneu.vydelka.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.ProductCard

@Composable
fun Favorites() {
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
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = stringResource(id = R.string.favorites_total_sum, 99999)
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                FilledTonalButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart_button_label),
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fakeProducts) {
                ProductCard(
                    title = it.name,
                    price = it.price,
                    contentDescription = "",
                    imageSrc = it.imageSrc,
                )
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