package com.hneu.vydelka.ui.profile.userinfo

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar
import com.hneu.vydelka.ui.order.components.UserOrderConfirmationInfoForm

@Composable
fun UserInfoScreen(onClose: () -> Unit, onProceed: () -> Unit) {
    val topAppBarNavigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.top_app_bar_dismiss_icon_description),
            )
        }
    }
    val topAppBarTitle: @Composable () -> Unit = {
        Text(stringResource(id = R.string.profile_user_info_edit_title))
    }
    val topAppBarActions: @Composable RowScope.() -> Unit = {
        TextButton(onClick = {
            onProceed()
        }) {
            Text(
                text= stringResource(id = R.string.profile_save_user_info),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
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