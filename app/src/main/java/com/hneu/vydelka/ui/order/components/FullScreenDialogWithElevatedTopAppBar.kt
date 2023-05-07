package com.hneu.vydelka.ui.order.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialogWithElevatedTopAppBar(
    topAppBarTitle: @Composable () -> Unit = {},
    topAppBarNavigationIcon: @Composable () -> Unit = {},
    topAppBarActions: @Composable RowScope.() -> Unit = {},
    onDismissRequest: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismissRequest() },
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    navigationIcon = topAppBarNavigationIcon,
                    title = topAppBarTitle,
                    actions = topAppBarActions
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            content(it)
        }
    }
}