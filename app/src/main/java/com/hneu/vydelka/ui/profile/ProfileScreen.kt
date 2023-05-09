package com.hneu.vydelka.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.profile.ordershistory.OrderHistoryScreen
import com.hneu.vydelka.ui.profile.userinfo.UserInfoScreen
import com.hneu.vydelka.ui.profile.viewhistory.ViewHistoryScreen

@Composable
fun Profile() {
    var openOrderHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openViewHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openAccountDetailsDialog by rememberSaveable { mutableStateOf(false) }
    var isUserLoggedIn = true
    when {
        openOrderHistoryDialog -> {
            OrderHistoryScreen(
                onClose = { openOrderHistoryDialog = false },
            )
        }
        openViewHistoryDialog -> {
            ViewHistoryScreen(
                onClose = { openViewHistoryDialog = false },
            )
        }
        openAccountDetailsDialog -> {
            UserInfoScreen(
                onClose = { openAccountDetailsDialog = false },
                onProceed = { openAccountDetailsDialog = false }
            )
        }
        else -> {
            if(isUserLoggedIn) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.3f),
                    ) {
                        Text(
                            text = "Ім'я Фамілія",
                            fontSize = 48.sp,
                            letterSpacing = 0.sp,
                        )
                    }
                    Column(
                        modifier = Modifier.weight(0.7f)
                    ) {
                        ListItem(
                            headlineContent = { Text(stringResource(id = R.string.profile_my_orders)) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.FormatListBulleted,
                                    contentDescription = stringResource(id = R.string.profile_my_orders),
                                )
                            },
                            modifier = Modifier.clickable {
                                openOrderHistoryDialog = true
                            }
                        )
                        Divider()
                        ListItem(
                            headlineContent = { Text(stringResource(id = R.string.profile_view_history)) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Visibility,
                                    contentDescription = stringResource(id = R.string.profile_view_history),
                                )
                            },
                            modifier = Modifier.clickable {
                                openViewHistoryDialog = true
                            }
                        )
                        Divider()
                        ListItem(
                            headlineContent = { Text(stringResource(id = R.string.profile_settings)) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.EditNote,
                                    contentDescription = stringResource(id = R.string.profile_settings),
                                )
                            },
                            modifier = Modifier.clickable {
                                openAccountDetailsDialog = true
                            }
                        )
                        Divider()
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_not_logged_in_message),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    FilledTonalButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = stringResource(id = R.string.profile_not_logged_in_button),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile()
}