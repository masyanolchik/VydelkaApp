package com.hneu.vydelka.ui.profile.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Room
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
fun RegisterScreen(
    onClose: () -> Unit = {},
    onProceed: (String, String, String, String, String, String, String) -> Unit =
        { _, _, _, _, _, _, _ -> },
) {
    var usernameTextFieldValue by rememberSaveable { mutableStateOf("") }
    var emailTextFieldValue by rememberSaveable { mutableStateOf("") }
    var passwordTextFieldValue by rememberSaveable { mutableStateOf("") }
    var nameTextFieldValue by rememberSaveable { mutableStateOf("") }
    var lastNameTextFieldValue by rememberSaveable { mutableStateOf("") }
    var phoneTextFieldValue by rememberSaveable { mutableStateOf("") }
    var addressTextFieldValue by rememberSaveable { mutableStateOf("") }
    var isUsernameNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isEmailNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isNewPasswordNotValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isNameValid by rememberSaveable {
        mutableStateOf(nameTextFieldValue.isNotEmpty())
    }
    var isLastNameValid by rememberSaveable {
        mutableStateOf(lastNameTextFieldValue.isNotEmpty())
    }
    var isPhoneNumberValid by rememberSaveable {
        mutableStateOf(phoneTextFieldValue.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneTextFieldValue).matches())
    }
    var isAddressValid by rememberSaveable {
        mutableStateOf(addressTextFieldValue.isNotEmpty())
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
        Text(stringResource(id = R.string.profile_register_button))
    }
    val topAppBarActions: @Composable RowScope.() -> Unit = {
        TextButton(onClick = {
            if(isNameValid && isLastNameValid && isPhoneNumberValid && isAddressValid) {
                onProceed(
                    usernameTextFieldValue,
                    emailTextFieldValue,
                    passwordTextFieldValue,
                    nameTextFieldValue,
                    lastNameTextFieldValue,
                    phoneTextFieldValue,
                    addressTextFieldValue
                )
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
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
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
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = stringResource(id = R.string.order_form_name_label),
                )
                OutlinedTextField(
                    value = nameTextFieldValue,
                    isError = !isNameValid,
                    onValueChange = {
                       nameTextFieldValue = it
                       isNameValid = it.isNotEmpty()
                    },
                    label = { Text(stringResource(id = R.string.order_form_name_label)) },
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                OutlinedTextField(
                    value = lastNameTextFieldValue,
                    isError = !isLastNameValid,
                    onValueChange = {
                        lastNameTextFieldValue = it
                        isLastNameValid = it.isNotEmpty()
                    },
                    label = { Text(stringResource(id = R.string.order_form_lastname_label)) },
                    modifier = Modifier.padding(start = 40.dp)
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = stringResource(id = R.string.order_form_phone_label),
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = phoneTextFieldValue,
                    isError = !isPhoneNumberValid,
                    onValueChange = {
                       phoneTextFieldValue = it
                       isPhoneNumberValid = it.isNotEmpty()
                    },
                    label = { Text(stringResource(id = R.string.order_form_phone_label)) },
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Room,
                    contentDescription = stringResource(id = R.string.order_form_address_label),
                )
                OutlinedTextField(
                    value = addressTextFieldValue,
                    isError = !isAddressValid,
                    onValueChange = {
                        addressTextFieldValue = it
                        isAddressValid = it.isNotEmpty()
                    },
                    label = { Text(stringResource(id = R.string.order_form_address_label)) },
                )
            }
        }
    }
}