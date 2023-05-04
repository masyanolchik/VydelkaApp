package com.hneu.vydelka.ui.feed.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
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
fun SmallCategoryButton(title:String, imageVector: ImageVector, contentDescription: String) {
    val buttonColor = MaterialTheme.colorScheme.secondaryContainer
    val onButtonColor = MaterialTheme.colorScheme.onSecondaryContainer
    Card(
        modifier = Modifier
            .height(86.dp)
            .width(86.dp),
        shape = ShapeDefaults.Large,
        backgroundColor = buttonColor,
        onClick = {},
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
                Canvas(modifier = Modifier.size(36.dp), onDraw = {
                    drawCircle(color = onButtonColor, radius = 36f)
                })
                Icon(
                    imageVector = imageVector,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(16.dp),
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
    SmallCategoryButton("Very very very long text", Icons.Outlined.PhoneIphone, "")
}