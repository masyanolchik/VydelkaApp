package com.hneu.vydelka.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.components.CartProductCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheetContent(
    onProceedOrderClick: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(0.9f)
        ) {
            CartProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
            CartProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
            CartProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
            CartProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
            CartProductCard(
                title = "Product Name Pro Max 256/16 GB",
                price = "9 999 ₴",
                contentDescription = "",
                imageSrc = "https://ireland.apollo.olxcdn.com/v1/files/bcvijdh1nnv41-UA/image;s=1000x700",
            )
        }
        Button(
            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
            // you must additionally handle intended state cleanup, if any.
            onClick = {
                onProceedOrderClick()
            },
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .fillMaxWidth(),
        ) {
            Text(stringResource(id = R.string.bottom_sheet_proceed_order))
        }
    }
}