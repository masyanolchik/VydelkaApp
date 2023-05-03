package com.hneu.vydelka.ui.feed.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallBoxProduct(
    title: String,
    price: String,
    imageSrc: String,
    contentDescription: String,
    onCardClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(100.dp),
        shape = ShapeDefaults.Medium,
        onClick = onCardClicked,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        ) {
            AsyncImage(
                model = imageSrc,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
                    .clip(ShapeDefaults.Small)
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            )

            Text(
                text = price,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            )
        }
    }
}

@Preview
@Composable
fun SmallBoxProductPreview() {
    SmallBoxProduct(title = "Product Name Pro Max 256/16 GB", price = "9 999 ₴", imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700", contentDescription = "") {

    }
}