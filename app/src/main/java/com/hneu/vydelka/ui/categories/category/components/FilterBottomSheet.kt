package com.hneu.vydelka.ui.categories.category.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.FilterModel
import com.hneu.vydelka.R
import java.math.BigDecimal
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    title: String = "",
    priceBoundStart: BigDecimal = BigDecimal(0),
    priceBoundEnd: BigDecimal = BigDecimal(100),
    attributesGroups: List<AttributeGroup> = emptyList(),
    selectedAttributes: List<Attribute> = emptyList(),
    onDismissButtonClicked:() -> Unit = {},
    onFilterApplyButtonClicked:(FilterModel) -> Unit = {},
) {
    var fromPriceTextFieldValue by rememberSaveable { mutableStateOf(priceBoundStart.toPlainString()) }
    var toPriceTextFieldValue by rememberSaveable { mutableStateOf(priceBoundEnd.toPlainString()) }
    var sliderPosition by remember { mutableStateOf(priceBoundStart.toFloat()..priceBoundEnd.toFloat()) }
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topAppBarRef, lowPriceRef, highPriceRef, priceRangeRef, groupListRef, filterButtonRef) = createRefs()
        val middleGuideline = createGuidelineFromStart(0.5f)
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
        OutlinedTextField(
            value = fromPriceTextFieldValue,
            onValueChange = {
               fromPriceTextFieldValue = it
            },
            label = { Text(stringResource(id = R.string.filter_price_from)) },
            modifier = Modifier
                .constrainAs(lowPriceRef) {
                    top.linkTo(topAppBarRef.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(middleGuideline, 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
        OutlinedTextField(
            value = toPriceTextFieldValue,
            onValueChange = {
                toPriceTextFieldValue = it
            },
            label = { Text(stringResource(id = R.string.filter_price_to)) },
            modifier = Modifier
                .constrainAs(highPriceRef) {
                    top.linkTo(topAppBarRef.bottom, 16.dp)
                    start.linkTo(middleGuideline, 8.dp)
                    end.linkTo(parent.end,16.dp)
                    width = Dimension.fillToConstraints
                }
        )
        RangeSlider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                fromPriceTextFieldValue = sliderPosition.start.roundToInt().toString()
                toPriceTextFieldValue = sliderPosition.endInclusive.roundToInt().toString()
            },
            valueRange = priceBoundStart.toFloat()..priceBoundEnd.toFloat(),
            onValueChangeFinished = { },
            startInteractionSource = startInteractionSource,
            endInteractionSource = endInteractionSource,
            modifier = Modifier.constrainAs(priceRangeRef) {
                top.linkTo(lowPriceRef.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(groupListRef.top, 16.dp)
                width = Dimension.fillToConstraints
            }
        )
        val selectedAttributesMap = mutableMapOf<AttributeGroup,Set<Attribute>>()
        LazyColumn(
            modifier = Modifier.constrainAs(groupListRef) {
                top.linkTo(priceRangeRef.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, 64.dp)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
        ) {
            items(attributesGroups) { attributeGroup ->
                AttributeGroupSection(
                    title = attributeGroup.name,
                    onAttributeSelected = { attribute, isSelected ->
                        if(isSelected) {
                            if(selectedAttributesMap[attributeGroup] == null) {
                                selectedAttributesMap[attributeGroup] = mutableSetOf()
                            }
                            (selectedAttributesMap[attributeGroup] as MutableSet).add(attribute)
                        } else {
                            selectedAttributesMap[attributeGroup]?.let {
                                (it as MutableSet).remove(attribute)
                                if(it.isEmpty()) {
                                    selectedAttributesMap.remove(attributeGroup)
                                }
                            }
                        }
                    },
                    selectedAttributes = selectedAttributes,
                    attributes = attributeGroup.attributes,
                )
            }
        }
        Button(
            onClick = {
                onFilterApplyButtonClicked(
                    FilterModel(
                        priceRangeStart = BigDecimal(fromPriceTextFieldValue),
                        priceRangeEnd = BigDecimal(toPriceTextFieldValue),
                        selectedAttributes = selectedAttributesMap,
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .fillMaxWidth()
                .constrainAs(filterButtonRef) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                },
        ) {
            Text(stringResource(id = R.string.apply))
        }
    }
}

@Composable
fun AttributeGroupSection(
    title: String = "",
    onAttributeSelected: (Attribute,Boolean) -> Unit = { _,_ -> },
    selectedAttributes: List<Attribute> = emptyList(),
    attributes: List<Attribute> = emptyList(),
) {
    var isSectionCollapsed by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if(isSectionCollapsed) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp
    Column {
        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
            IconButton(
                onClick = { isSectionCollapsed = !isSectionCollapsed },
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Crossfade(targetState = icon, label = "") {
                    Icon(
                        imageVector = it,
                        contentDescription = title,
                    )
                }
            }
        }
        AnimatedVisibility(visible = !isSectionCollapsed) {
            Column {
                attributes.forEach {attribute ->
                    var checkedState by rememberSaveable { mutableStateOf(selectedAttributes.contains(attribute)) }
                    onAttributeSelected(attribute, checkedState)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .toggleable(
                                value = checkedState,
                                onValueChange = {
                                    checkedState = it
                                    onAttributeSelected(attribute, it)
                                },
                                role = Role.Checkbox
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = null
                        )
                        Text(
                            text = attribute.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun AttributeSectionPreview() {
    AttributeGroupSection(
        title = "hello world",
        attributes = listOf(
            Attribute(0, "Attribute 1"),
            Attribute(1, "Attribute 2"),
            Attribute(2, "Attribute 3"),
        )
    )
}