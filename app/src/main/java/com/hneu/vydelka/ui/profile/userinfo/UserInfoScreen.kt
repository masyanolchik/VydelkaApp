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
fun UserInfoScreen(onClose: () -> Unit, onProceed: () -> Unit) {
    var nameText by rememberSaveable { mutableStateOf("") }
    var lastNameText by rememberSaveable { mutableStateOf("") }
    var phoneText by rememberSaveable { mutableStateOf("") }
    var addressText by rememberSaveable { mutableStateOf("") }
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
                nameText = it
                isNameValid = it.isNotEmpty()
            },
            isNameValid = isNameValid,
            onLastNameTextFieldValueChanged = {
                lastNameText = it
                isLastNameValid = it.isNotEmpty()
            },
            isLastNameValid = isLastNameValid,
            onPhoneTextFieldValueChanged = {
                phoneText = it
                isPhoneNumberValid = phoneText.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneText).matches()
            },
            isPhoneNumberValid = isPhoneNumberValid,
            onAddressTextFieldValueChanged = {
                addressText = it
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
