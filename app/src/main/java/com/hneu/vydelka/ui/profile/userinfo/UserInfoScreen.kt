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
import androidx.compose.ui.tooling.preview.Preview
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.order.components.FullScreenDialogWithElevatedTopAppBar
import com.hneu.vydelka.ui.order.components.UserOrderConfirmationInfoForm

@Composable
fun UserInfoScreen(
    nameText: String = "",
    onNameTextFieldChanged: (String) -> Unit = {},
    lastNameText: String = "",
    onLastNameTextFieldChanged: (String) -> Unit = {},
    phoneText: String = "",
    onPhoneTextFieldChanged: (String) -> Unit = {},
    addressText: String = "",
    onAddressTextFieldChanged: (String) -> Unit = {},
    onClose: () -> Unit = {},
    onProceed: () -> Unit = {},
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
            if(isNameValid && isLastNameValid && isPhoneNumberValid && isAddressValid) {
                onProceed()
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
        UserOrderConfirmationInfoForm(
            paddingValues = it,
            nameTextFieldValue = nameText,
            lastNameTextFieldValue = lastNameText,
            phoneTextFieldValue = phoneText,
            addressTextFieldValue = addressText,
            onNameTextFieldValueChanged = {
                onNameTextFieldChanged(it)
                isNameValid = it.isNotEmpty()
            },
            isNameValid = isNameValid,
            onLastNameTextFieldValueChanged = {
                onLastNameTextFieldChanged(it)
                isLastNameValid = it.isNotEmpty()
            },
            isLastNameValid = isLastNameValid,
            onPhoneTextFieldValueChanged = {
                onPhoneTextFieldChanged(it)
                isPhoneNumberValid = phoneText.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneText).matches()
            },
            isPhoneNumberValid = isPhoneNumberValid,
            onAddressTextFieldValueChanged = {
                onAddressTextFieldChanged(it)
                isAddressValid = it.isNotEmpty()
            },
            isAddressValid = isAddressValid,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserInfo() {
    UserInfoScreen(onClose = { /*TODO*/ }) {

    }
}
