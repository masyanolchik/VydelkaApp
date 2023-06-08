package com.hneu.vydelka.ui.profile.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar

@Composable
fun ForgotPasswordScreen(
    onClose: () -> Unit = {},
    onProceed: (String, String, String) -> Unit = { _, _, _ -> },
) {
    var usernameTextFieldValue by rememberSaveable { mutableStateOf("") }
    var emailTextFieldValue by rememberSaveable { mutableStateOf("") }
    var passwordTextFieldValue by rememberSaveable { mutableStateOf("") }
    var isUsernameNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isEmailNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isNewPasswordNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(stringResource(id = R.string.profile_forgot_password_title))
    }
    val topAppBarActions: @Composable RowScope.() -> Unit = {
        TextButton(onClick = {
            if(!isUsernameNotValid && !isEmailNotValid && !isNewPasswordNotValid) {
                onProceed(usernameTextFieldValue,emailTextFieldValue, passwordTextFieldValue)
            }
        }) {
            Text(
                text= stringResource(id = R.string.profile_save_user_info),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

    FullScreenDialogWithElevatedTopAppBar(
        topAppBarTitle = topAppBarTitle,
        topAppBarNavigationIcon = topAppBarNavigationIcon,
        topAppBarActions = topAppBarActions,
    ) {
        Column(
            modifier = Modifier.padding(it)) {
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
                    isError = isUsernameNotValid,
                    onValueChange = {
                        usernameTextFieldValue = it
                        isUsernameNotValid = it.isEmpty()
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
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(id = R.string.profile_email_text_field),
                )
                OutlinedTextField(
                    value = emailTextFieldValue,
                    isError = isUsernameNotValid,
                    onValueChange = {
                        emailTextFieldValue = it
                        isEmailNotValid = !it.matches(Regex("^\\S+@\\S+\\.\\S+$"))
                    },
                    label = { Text(stringResource(id = R.string.profile_email_text_field)) },
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
                    isError = isNewPasswordNotValid,
                    onValueChange = {
                        passwordTextFieldValue = it
                        isNewPasswordNotValid = it.length < 8
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text(stringResource(id = R.string.profile_password_text_field)) },
                )
            }
        }
    }
}

