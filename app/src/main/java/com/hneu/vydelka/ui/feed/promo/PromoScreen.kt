package com.hneu.vydelka.ui.feed.promo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.promo.Promo
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.ui.navigation.BottomMenuItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PromoScreen(
    tagsMutableStateFlow: MutableStateFlow<List<Tag>> = MutableStateFlow(emptyList()),
    navController: NavHostController = rememberNavController(),
    promoViewModel: PromoViewModel = hiltViewModel(),
    promoId: Int = 0,
    onClose: () -> Unit = {},
) {
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val promo by promoViewModel.getPromoById(promoId).collectAsStateWithLifecycle()
    when(promo) {
        is Result.Success -> {
            val promoObject = (promo as Result.Success<Promo>).data
            val topAppBarTitle: @Composable () -> Unit = {
                Text(promoObject.name)
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
                            model = promoObject.titleImageSrc,
                            contentDescription = promoObject.name,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                        )
                        Text(
                            text = promoObject.detailedDescription,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Button(
                            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                            // you must additionally handle intended state cleanup, if any.
                            onClick = {
                                onClose()
                                tagsMutableStateFlow.value = promoObject.tags
                                navController.navigate(BottomMenuItem.CatalogueScreen.route)
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
        is Result.Error -> {
            val k = 0
        }
        else -> {
            CircularProgressIndicator()
        }
    }
}