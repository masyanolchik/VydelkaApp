package com.hneu.vydelka.ui.feed

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.*
import com.hneu.vydelka.ui.feed.testing.MockData

@Composable
fun Feed() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        val promoImageAdapter = PromoImageAdapter()
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
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
            SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
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
                onCardClicked = {},
                onCartAddedClicked = {},
            )
            MediumBoxProductCard(
                "Product Name Pro Max 256/16 GB",
                "9 999 ₴",
                "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                "",
                onCardClicked = {},
                onCartAddedClicked = {},
            )
            MediumBoxProductCard(
                "Product Name Pro Max 256/16 GB",
                "9 999 ₴",
                "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                "",
                onCardClicked = {},
                onCartAddedClicked = {},
            )
            MediumBoxProductCard(
                "Product Name Pro Max 256/16 GB",
                "9 999 ₴",
                "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                "",
                onCardClicked = {},
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
            ) {}
            SmallBoxProduct(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                contentDescription = "",
            ) {}
            SmallBoxProduct(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                contentDescription = "",
            ) {}
            SmallBoxProduct(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                contentDescription = "",
            ) {}
            SmallBoxProduct(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
                contentDescription = "",
            ) {}
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
            )
            ProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
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
            )
            ProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
        }
    }
}