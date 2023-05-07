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
fun OrderConfirmationForm(onClose: () -> Unit, onProceed: () -> Unit) {
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
        TextButton(onClick = {
            onProceed()
        }) {
            Text(text= stringResource(id = R.string.order_form_next_label))
        }
    }

    var nameText by rememberSaveable { mutableStateOf("") }
    var lastNameText by rememberSaveable { mutableStateOf("") }
    var phoneText by rememberSaveable { mutableStateOf("") }
    var addressText by rememberSaveable { mutableStateOf("") }

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
            onNameTextFieldValueChanged = { changedText -> nameText = changedText},
            onLastNameTextFieldValueChanged = { changedText -> lastNameText = changedText},
            onPhoneTextFieldValueChanged = { changedText -> phoneText = changedText },
            onAddressTextFieldValueChanged = { changedText -> addressText = changedText },
        )
    }
}