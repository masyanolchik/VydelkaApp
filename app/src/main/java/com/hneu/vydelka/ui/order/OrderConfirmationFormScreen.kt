package com.hneu.vydelka.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar
import com.hneu.vydelka.ui.order.components.UserOrderConfirmationInfoForm

@Composable
fun OrderConfirmationForm(
    onClose: () -> Unit = {},
    onProceed: () -> Unit = {},
    onError: (String) -> Unit = {},
    nameText: String = "",
    onNameTextFieldValueChanged: (String) -> Unit = { },
    lastNameText: String = "",
    onLastNameTextFieldValueChanged: (String) -> Unit = { },
    phoneText: String = "",
    onPhoneTextFieldValueChanged: (String) -> Unit = { },
    addressText: String = "",
    onAddressTextFieldValueChanged: (String) -> Unit = { },
 ) {
    var isNameValid by rememberSaveable {
        mutableStateOf(nameText.isNotEmpty())
    }
    var isLastNameValid by rememberSaveable {
        mutableStateOf(lastNameText.isNotEmpty())
    }
    var isPhoneNumberValid by rememberSaveable {
        mutableStateOf(phoneText.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneText).matches())
    }
    var isAddressValid by rememberSaveable {
        mutableStateOf(addressText.isNotEmpty())
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(stringResource(id = R.string.bottom_sheet_proceed_order))
    }
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description)
            )
        }
    }
    val topAppBarActions: @Composable RowScope.() -> Unit = {
        val errorText = stringResource(id = R.string.order_confirmation_validation_error)
        TextButton(onClick = {
            if(isNameValid && isLastNameValid && isPhoneNumberValid && isAddressValid) {
                onProceed()
            } else {
                onError(errorText)
            }
        }) {
            Text(text= stringResource(id = R.string.order_form_next_label))
        }
    }

    FullScreenDialogWithElevatedTopAppBar(
        topAppBarTitle = topAppBarTitle,
        topAppBarNavigationIcon = topAppBarNavigationIcon,
        topAppBarActions = topAppBarActions,
    ) {
        UserOrderConfirmationInfoForm(
            paddingValues = it,
            nameTextFieldValue = nameText,
            lastNameTextFieldValue = lastNameText,
            phoneTextFieldValue = phoneText,
            addressTextFieldValue = addressText,
            onNameTextFieldValueChanged = {
                onNameTextFieldValueChanged(it)
                isNameValid = it.isNotEmpty()
            },
            isNameValid = isNameValid,
            onLastNameTextFieldValueChanged = {
                onLastNameTextFieldValueChanged(it)
                isLastNameValid = it.isNotEmpty()
            },
            isLastNameValid = isLastNameValid,
            onPhoneTextFieldValueChanged = {
                onPhoneTextFieldValueChanged(it)
                isPhoneNumberValid = phoneText.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneText).matches()
            },
            isPhoneNumberValid = isPhoneNumberValid,
            onAddressTextFieldValueChanged = {
                onAddressTextFieldValueChanged(it)
                isAddressValid = it.isNotEmpty()
            },
            isAddressValid = isAddressValid,
        )
    }
}