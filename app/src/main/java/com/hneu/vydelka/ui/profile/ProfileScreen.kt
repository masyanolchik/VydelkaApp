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
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hneu.core.domain.user.User
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.profile.forgotpassword.ForgotPasswordScreen
import com.hneu.vydelka.ui.profile.ordershistory.OrderHistoryScreen
import com.hneu.vydelka.ui.profile.register.RegisterScreen
import com.hneu.vydelka.ui.profile.userinfo.UserInfoScreen
import com.hneu.vydelka.ui.profile.viewhistory.ViewHistoryScreen
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.accountmanager.AccountManagerImpl


@Composable
fun Profile(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    var openOrderHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openViewHistoryDialog by rememberSaveable { mutableStateOf(false) }
    var openAccountDetailsDialog by rememberSaveable { mutableStateOf(false) }
    var openForgotPasswordDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var openRegisterProfile by rememberSaveable {
        mutableStateOf(false)
    }
    val currentUser by profileViewModel.currentUser.collectAsStateWithLifecycle()
    val userRegisteredState by profileViewModel.userRegisterState.collectAsStateWithLifecycle()
    val passwordChangedState by profileViewModel.passwordChangedState.collectAsStateWithLifecycle()
    when(passwordChangedState) {
        is Result.Completed -> {
            LaunchedEffect(userRegisteredState) {
                snackbarHostState.showSnackbar("Пароль успішно змінено", duration = SnackbarDuration.Long)
            }
        }
        is Result.Error -> {
            LaunchedEffect(userRegisteredState) {
                snackbarHostState.showSnackbar("Відбулася помилка при зміні паролю, спробуйте ще раз", duration = SnackbarDuration.Long)
            }
        }
        else -> {}
    }
    when(userRegisteredState) {
        is Result.Completed -> {
            LaunchedEffect(userRegisteredState) {
                snackbarHostState.showSnackbar("Профіль створено", duration = SnackbarDuration.Long)
            }
        }
        is Result.Error -> {
            LaunchedEffect(userRegisteredState) {
                snackbarHostState.showSnackbar("Відбулася помилка при створенні профілю, спробуйте ще раз", duration = SnackbarDuration.Long)
            }
        }
        else -> {}
    }
    when {
        openForgotPasswordDialog -> {
            ForgotPasswordScreen(
                onClose = { openForgotPasswordDialog = false },
                onProceed = { username, email, password ->
                    openForgotPasswordDialog = false
                    profileViewModel.changePassword(username,email,password)
                }
            )
        }
        openRegisterProfile -> {
            RegisterScreen(
                onClose = { openRegisterProfile = false },
                onProceed = { username, email, password, name, lastName, phone, address ->
                    openRegisterProfile = false
                    profileViewModel.registerUser(username, email, password, name, lastName, phone, address)
                }
            )
        }
        else -> {}
    }
    when(currentUser) {
        is Result.Success -> {
            val userObject = (currentUser as Result.Success<User>).data
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
                    var nameText by rememberSaveable {
                        mutableStateOf(userObject.name)
                    }
                    var lastNameText by rememberSaveable {
                        mutableStateOf(userObject.lastName)
                    }
                    var phoneText by rememberSaveable {
                        mutableStateOf(userObject.phoneNumber)
                    }
                    var addressText by rememberSaveable {
                        mutableStateOf(userObject.shippingAddress)
                    }
                    UserInfoScreen(
                        nameText = nameText,
                        onNameTextFieldChanged = { nameText = it },
                        lastNameText = lastNameText,
                        onLastNameTextFieldChanged = { lastNameText = it},
                        phoneText = phoneText,
                        onPhoneTextFieldChanged = { phoneText = it },
                        addressText = addressText,
                        onAddressTextFieldChanged = { addressText = it },
                        onClose = { openAccountDetailsDialog = false },
                        onProceed = {
                            openAccountDetailsDialog = false
                            profileViewModel.changeContacts(nameText, lastNameText, phoneText, addressText)
                        },
                    )
                }
            }
            if(!userObject.equals(AccountManagerImpl.UNREGISTERED_USER)) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.3f),
                    ) {
                        Text(
                            text = "${userObject.name} ${userObject.lastName}",
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
                                profileViewModel.signOut()
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
                            onClick = {
                                profileViewModel.loginWithUsernameAndPassword(usernameTextFieldValue, passwordTextFieldValue)
                            },
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
        is Result.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        }
        is Result.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Трапилася помилка, що робити хз",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        else -> {}
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile()
}