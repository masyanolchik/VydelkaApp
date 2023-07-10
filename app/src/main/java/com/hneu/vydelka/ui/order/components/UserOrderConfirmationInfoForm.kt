package com.hneu.vydelka.ui.order.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hneu.vydelka.R

@Composable
fun UserOrderConfirmationInfoForm(
    nameTextFieldValue: String = "",
    onNameTextFieldValueChanged: (String) -> Unit = { },
    isNameValid: Boolean = true,
    lastNameTextFieldValue: String = "",
    onLastNameTextFieldValueChanged: (String) -> Unit = { },
    isLastNameValid: Boolean = true,
    phoneTextFieldValue: String = "",
    onPhoneTextFieldValueChanged: (String) -> Unit = { },
    isPhoneNumberValid: Boolean = true,
    addressTextFieldValue: String = "",
    onAddressTextFieldValueChanged: (String) -> Unit = { },
    isAddressValid: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(),
    checkedState: Boolean = false,
    onCheckedStateChange: (Boolean) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
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
                contentDescription = stringResource(id = R.string.order_form_name_label),
            )
            OutlinedTextField(
                value = nameTextFieldValue,
                isError = !isNameValid,
                onValueChange = onNameTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_name_label)) },
            )
        }
        if(!isNameValid) {
            Text(
                modifier = Modifier.padding(start = 56.dp),
                text = stringResource(R.string.user_order_confirmation_validation_required_field_error),
                fontSize = 14.sp,
                color = Color.Red
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            OutlinedTextField(
                value = lastNameTextFieldValue,
                isError = !isLastNameValid,
                onValueChange = onLastNameTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_lastname_label)) },
                modifier = Modifier.padding(start = 40.dp)
            )
        }
        if(!isLastNameValid) {
            Text(
                modifier = Modifier.padding(start = 56.dp),
                text = stringResource(R.string.user_order_confirmation_validation_required_field_error),
                fontSize = 14.sp,
                color = Color.Red
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
                onValueChange = onPhoneTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_phone_label)) },
            )
        }
        if(!isPhoneNumberValid) {
            Text(
                modifier = Modifier.padding(start = 56.dp),
                text = stringResource(R.string.user_order_confirmation_validation_phone_error),
                fontSize = 14.sp,
                color = Color.Red
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
                onValueChange = onAddressTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_address_label)) },
            )
        }
        if(!isAddressValid) {
            Text(
                modifier = Modifier.padding(start = 56.dp),
                text = stringResource(R.string.user_order_confirmation_validation_required_field_error),
                fontSize = 14.sp,
                color = Color.Red
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .toggleable(
                    value = checkedState,
                    onValueChange = {
                        onCheckedStateChange(it)
                    },
                    role = Role.Checkbox
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = null
            )
            Text(
                text = stringResource(id = R.string.confirm_process_user_data),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun UserOrderConfirmationInfoFormPreview() {
    UserOrderConfirmationInfoForm(
        isNameValid = false,
        isAddressValid = false,
        isLastNameValid = false,
        isPhoneNumberValid = false,
    )
}