package com.hneu.vydelka.ui.feed

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.hneu.vydelka.ui.feed.testing.MockData

@Composable
fun Feed() {
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        val promoImageAdapter = PromoImageAdapter()
        AndroidView(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(196.dp),
            factory = { context -> RecyclerView(context).apply {
                layoutManager = CarouselLayoutManager()
                adapter = promoImageAdapter
                clipChildren = false
                clipToPadding = false
            } },
            update = { promoImageAdapter.submitList(MockData.imageList) },
        )
    }
}