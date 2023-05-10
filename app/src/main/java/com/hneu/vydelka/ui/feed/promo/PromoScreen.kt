package com.hneu.vydelka.ui.feed.promo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar

@Composable
fun PromoScreen(title:String,onClose: () -> Unit) {
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(title)
    }
    FullScreenDialogWithElevatedTopAppBar(
        topAppBarTitle = topAppBarTitle,
        topAppBarNavigationIcon = topAppBarNavigationIcon,
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = "https://images.wallpapersden.com/image/download/landscape-pixel-art_bGhnaGeUmZqaraWkpJRraWWtaW1l.jpg",
                    contentDescription = title,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                )
                Text(
                    text = "LALALALALLALALALLALALLALALALLALALALLALALALLALALLALALALALALLALALLALALALLALALALLALALLALALALLALALALLALALALALLALALALALLALALALALLALALALALALALLALALALALALALLALALALALA",
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        //
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                ) {
                    Text(stringResource(id = R.string.continue_to_promo))
                }
            }
        }
    }
}