package com.hneu.vydelka.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.profile.forgotpassword.ForgotPasswordScreen
import com.hneu.vydelka.ui.profile.ordershistory.OrderHistoryScreen
import com.hneu.vydelka.ui.profile.register.RegisterScreen
import com.hneu.vydelka.ui.profile.userinfo.UserInfoScreen
import com.hneu.vydelka.ui.profile.viewhistory.ViewHistoryScreen

@Composable
fun Profile(navController: NavHostController = rememberNavController()) {
    var openOrderHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openViewHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openAccountDetailsDialog by rememberSaveable { mutableStateOf(false) }
    var openForgotPasswordDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var openRegisterProfile by rememberSaveable {
        mutableStateOf(false)
    }
    var isUserLoggedIn by rememberSaveable {
        mutableStateOf(false)
    }
    when {
        openOrderHistoryDialog -> {
            OrderHistoryScreen(
                onClose = { openOrderHistoryDialog = false },
            )
        }
        openViewHistoryDialog -> {
            ViewHistoryScreen(
                navController = navController,
                onClose = { openViewHistoryDialog = false },
            )
        }
        openAccountDetailsDialog -> {
            UserInfoScreen(
                onClose = { openAccountDetailsDialog = false },
                onProceed = { openAccountDetailsDialog = false }
            )
        }
        openForgotPasswordDialog -> {
            ForgotPasswordScreen(
                onClose = { openForgotPasswordDialog = false },
                onProceed = { _, _, _ -> openForgotPasswordDialog = false }
            )
        }
        openRegisterProfile -> {
            RegisterScreen(
                onClose = { openRegisterProfile = false },
                onProceed = { _, _, _, _, _, _, _ -> openRegisterProfile = false }
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
                        ListItem(
                            headlineContent = { Text(stringResource(id = R.string.profile_log_out_button)) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.ExitToApp,
                                    contentDescription = stringResource(id = R.string.profile_log_out_button),
                                )
                            },
                            modifier = Modifier.clickable {
                                isUserLoggedIn = false
                            }
                        )
                        Divider()
                    }
                }
            } else {
                var isAuthorizationFailed by rememberSaveable { mutableStateOf(false) }
                var usernameTextFieldValue by rememberSaveable { mutableStateOf("") }
                var passwordTextFieldValue by rememberSaveable {
                    mutableStateOf("")
                }
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
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = stringResource(id = R.string.profile_username_text_field),
                        )
                        OutlinedTextField(
                            value = usernameTextFieldValue,
                            isError = isAuthorizationFailed,
                            onValueChange = {
                               usernameTextFieldValue = it
                            },
                            label = { Text(stringResource(id = R.string.profile_username_text_field)) },
                        )
                    }
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Key,
                            contentDescription = stringResource(id = R.string.profile_password_text_field),
                        )
                        OutlinedTextField(
                            value = passwordTextFieldValue,
                            isError = isAuthorizationFailed,
                            onValueChange = {
                                passwordTextFieldValue = it
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            label = { Text(stringResource(id = R.string.profile_password_text_field)) },
                        )
                    }
                    Row {
                        FilledTonalButton(
                            onClick = { isUserLoggedIn = true },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.profile_not_logged_in_button),
                            )
                        }
                        FilledTonalButton(onClick = {
                            openRegisterProfile = true
                        }) {
                            Text(
                                text = stringResource(id = R.string.profile_register_button),
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = {
                            openForgotPasswordDialog = true
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.profile_forgot_password_button),
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