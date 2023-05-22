package com.hneu.vydelka.ui.categories.components

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediumCategoryCard(
    title:String = "",
    imageVector: ImageVector = Icons.Outlined.Error,
    contentDescription: String = "",
    selected:Boolean = false,
    onClick: () -> Unit = {},
) {
    val buttonColor = if(selected)MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val onButtonColor = if(selected)MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
    Card(
        modifier = Modifier
            .height(110.dp)
            .width(110.dp),
        shape = ShapeDefaults.Large,
        backgroundColor = buttonColor,
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp, end = 4.dp, top = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Canvas(modifier = Modifier.size(64.dp), onDraw = {
                    drawCircle(color = onButtonColor, radius = 64f)
                })
                Icon(
                    imageVector = imageVector,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(36.dp),
                )
            }
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryButtonPreview() {
    MediumCategoryCard("Very very very long text", Icons.Outlined.PhoneIphone, "")
}