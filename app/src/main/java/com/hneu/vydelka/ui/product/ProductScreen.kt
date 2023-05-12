package com.hneu.vydelka.ui.product

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.feed.PromoImageAdapter
import com.hneu.vydelka.ui.feed.testing.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, id:Int) {
    val promoImageAdapter = PromoImageAdapter(true) {}
    var characteristics = listOf(
        Pair("Назва характеристики","значення"),
        Pair("Назва характеристики","значення"),
        Pair("Назва характеристики","значення"),
        Pair("Назва характеристики","значення"),
        Pair("Назва характеристики","значення"),
        Pair("Назва характеристики","значення"),
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description)
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = topAppBarNavigationIcon,
                title = {},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.add_to_favorites_button_label)
                        )
                    }
                },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                expanded = false,
                icon = {
                   Icon(
                       imageVector = Icons.Outlined.AddShoppingCart,
                       contentDescription = stringResource(id = R.string.add_to_cart_button_label),
                   )
                },
                text = {
                   Text(stringResource(R.string.add_to_cart_button_label))
                },
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                AndroidView(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 16.dp),
                    factory = { context -> RecyclerView(context).apply {
                        layoutManager = CarouselLayoutManager()
                        adapter = promoImageAdapter
                        clipChildren = false
                        clipToPadding = false
                    } },
                    update = { promoImageAdapter.submitList(MockData.imageList) },
                )
                Text(
                    text = stringResource(R.string.product_code_label, 1258022),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp,
                )
                Text(
                    text = "Product Name Pro Max 256/16 GB",
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.25.sp,
                    style = LocalTextStyle.current.copy(lineHeight = 34.sp),
                )
                Text(
                    text = stringResource(R.string.product_price_label,9999),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                )
                Text(
                    text = "В наявності",
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                )
                Text(
                    text = stringResource(R.string.product_warranty_label,60),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                )
                Text(
                    text = stringResource(R.string.product_return_label,14),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                )

                Divider(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                )
                Text(
                    text = stringResource(R.string.product_characteristics_label),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                )
            }
            items(characteristics) {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Text(
                        text = it.first,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(0.5f),
                    )
                    Text(
                        text = it.second,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(0.5f),
                    )
                }
            }

            item {
                Divider(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                )
                Text(
                    text = stringResource(R.string.product_description_label),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    style = LocalTextStyle.current.copy(lineHeight = 24.sp),
                )
                Text(
                    text = LOREM_IPSUM,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum pellentesque lectus sed molestie dictum. Suspendisse quis urna mollis, gravida nunc eget, ornare nibh. Duis ullamcorper purus orci, sed dapibus diam aliquet sed. Nulla mollis in libero at molestie. Nullam feugiat id purus ac condimentum. In sit amet venenatis augue. Curabitur volutpat blandit elit, sit amet tincidunt augue interdum ut. Nullam at cursus dui. Nam facilisis arcu sed ex feugiat mattis. Donec non varius eros, at feugiat risus.\n" +
        "\n" +
        "Suspendisse gravida, sem quis dictum ullamcorper, elit felis rhoncus felis, a egestas mi neque vitae felis. Aliquam scelerisque varius nisi at ullamcorper. Donec imperdiet pellentesque risus, id tincidunt diam fermentum et. Nulla rutrum felis volutpat mauris consequat scelerisque. Vestibulum porta volutpat diam sit amet auctor. Proin fringilla, sapien nec vulputate consectetur, neque risus vestibulum odio, id auctor leo est sit amet massa. Suspendisse feugiat scelerisque mauris, vel varius augue efficitur non. Phasellus facilisis massa tortor, eu porttitor dolor tempus sit amet. Duis vulputate non sem eu mollis. Etiam vitae pellentesque metus. Proin a arcu interdum, mollis ipsum id, lobortis risus."