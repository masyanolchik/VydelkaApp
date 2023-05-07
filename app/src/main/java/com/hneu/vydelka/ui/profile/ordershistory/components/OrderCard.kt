package com.hneu.vydelka.ui.profile.ordershistory.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.favorites.DummyProduct
import com.hneu.vydelka.ui.feed.components.SmallBoxProduct

@Composable
fun OrderCard(
    orderNumber: String,
    orderStatus: String,
    orderDate: String,
    orderTotalSum: String,
    productList: List<DummyProduct>,
    modifier: Modifier = Modifier,
) {
    var isProductListExpanded by rememberSaveable { mutableStateOf(false) }
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
        ) {
            val (orderNumberRef, orderDateRef, orderStatusRef, expandButtonRef, lazyProductRowRef, orderTotalRef) = createRefs()
            Text(
                text = stringResource(id = R.string.order_history_order_number_label, orderNumber),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(orderNumberRef) {
                        top.linkTo(parent.top, 8.dp)
                        start.linkTo(parent.start, 8.dp)
                        end.linkTo(orderDateRef.start, 4.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = orderDate,
                modifier = Modifier
                    .constrainAs(orderDateRef) {
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                    }
            )
            Text(
                text = stringResource(id = R.string.order_history_order_status_label,orderStatus),
                modifier = Modifier
                    .constrainAs(orderStatusRef) {
                        top.linkTo(orderNumberRef.bottom, 4.dp)
                        start.linkTo(parent.start, 8.dp)
                        end.linkTo(orderNumberRef.end)
                        width = Dimension.fillToConstraints
                    }
            )
            val onExpandClick = {
                isProductListExpanded = !isProductListExpanded
            }
            if(isProductListExpanded) {
                IconButton(
                    onClick = onExpandClick,
                    modifier = Modifier.constrainAs(expandButtonRef) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(orderDateRef.bottom, 4.dp)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ExpandLess,
                        contentDescription = stringResource(id = R.string.order_history_order_products_expand_collapse_button_description),
                    )
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.constrainAs(lazyProductRowRef) {
                        top.linkTo(expandButtonRef.bottom, 4.dp)
                        start.linkTo(parent.start, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    items(productList) {
                        SmallBoxProduct(
                            title = it.name,
                            price = it.price,
                            imageSrc = it.imageSrc,
                            contentDescription =it.contentDescription,
                        ) {}
                    }
                }
                Text(
                    text = stringResource(id = R.string.order_history_order_total_sum_label, orderTotalSum),
                    modifier = Modifier.constrainAs(orderTotalRef) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(lazyProductRowRef.bottom,4.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                )
            } else {
                IconButton(
                    onClick = onExpandClick,
                    modifier = Modifier.constrainAs(expandButtonRef) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(orderDateRef.bottom, 4.dp)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ExpandMore,
                        contentDescription = stringResource(id = R.string.order_history_order_products_expand_collapse_button_description),
                    )
                }
                Text(
                    text = stringResource(id = R.string.order_history_order_total_sum_label, orderTotalSum),
                    modifier = Modifier.constrainAs(orderTotalRef) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(expandButtonRef.bottom,4.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderCard () {
    val productList = listOf(
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
    )
    OrderCard(
        "239849238942",
        "В роботі",
        "21.08.2020 08:20",
        "110 000 ₴",
        productList,
    )
}

data class DummyOrder(
    val orderNumber: String,
    val orderStatus: String,
    val orderDate: String,
    val orderTotalSum: String,
    val productList: List<DummyProduct>,
)