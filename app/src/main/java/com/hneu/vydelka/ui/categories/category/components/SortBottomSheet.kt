package com.hneu.vydelka.ui.categories.category.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.categories.category.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    title: String = "",
    onDismissButtonClicked:() -> Unit = {},
    currentSelectedMethod: CategoryViewModel.SortingMethods = CategoryViewModel.SortingMethods.LOW_PRICE,
    onSortOptionSelected:(CategoryViewModel.SortingMethods) -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        val (topAppBarRef, lowPriceRef, highPriceRef) = createRefs()
        TopAppBar(
            navigationIcon = { IconButton (onClick = onDismissButtonClicked){
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = title
                    )
                }
            },
            title = {
                Text(text = title)
            },
            modifier = Modifier.constrainAs(topAppBarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        val lowPriceText = stringResource(id = R.string.low_price_sorting_method)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(lowPriceRef) {
                top.linkTo(topAppBarRef.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ){
            RadioButton(
                selected = currentSelectedMethod == CategoryViewModel.SortingMethods.LOW_PRICE,
                onClick = { onSortOptionSelected(CategoryViewModel.SortingMethods.LOW_PRICE) },
                modifier = Modifier.semantics { contentDescription = lowPriceText }
            )
            Text(
                text = lowPriceText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                )
        }
        val highPriceText = stringResource(id = R.string.high_price_sorting_method)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(highPriceRef) {
                    top.linkTo(lowPriceRef.bottom, 4.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ){
            RadioButton(
                selected = currentSelectedMethod == CategoryViewModel.SortingMethods.HIGH_PRICE,
                onClick = { onSortOptionSelected(CategoryViewModel.SortingMethods.HIGH_PRICE) },
                modifier = Modifier.semantics { contentDescription = highPriceText }
            )
            Text(
                text = highPriceText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            )
        }

    }

}

@Preview
@Composable
fun SortBottomSheetPreview() {
    SortBottomSheet()
}