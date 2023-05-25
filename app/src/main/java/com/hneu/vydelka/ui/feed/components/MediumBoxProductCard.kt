package com.hneu.vydelka.ui.feed.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hneu.vydelka.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumBoxProductCard(
    title: String,
    price: String,
    imageSrc: String,
    contentDescription: String,
    onCardClicked: () -> Unit,
    onCartAddedClicked: () -> Unit,
    isProductAddedToCart: Boolean = false,
) {
    Card(
        modifier = Modifier
            .width(120.dp),
        shape = ShapeDefaults.Medium,
        onClick = onCardClicked,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        ) {
            AsyncImage(
                model = imageSrc,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
                    .clip(ShapeDefaults.Small)
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 8.dp)
            )
            Text(
                text = price,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 8.dp)
            )
            if(isProductAddedToCart) {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    onClick = {
                        onCartAddedClicked.invoke()
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart_button_label_added),
                        textAlign = TextAlign.Center,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            } else {
                ElevatedButton(
                    onClick = {
                        onCartAddedClicked.invoke()
                    }, modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_cart_button_label),
                        textAlign = TextAlign.Center,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBoxProductCard() {
    MediumBoxProductCard(
        "Product Name Pro Max 256/16 GB",
        "9 999 ₴",
        "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
        "",
    onCardClicked = {},
    onCartAddedClicked = {},
    )
}