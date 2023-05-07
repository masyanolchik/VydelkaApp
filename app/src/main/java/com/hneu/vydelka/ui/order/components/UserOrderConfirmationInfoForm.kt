package com.hneu.vydelka.ui.order.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hneu.vydelka.R

@Composable
fun UserOrderConfirmationInfoForm(
    nameTextFieldValue: String,
    onNameTextFieldValueChanged: (String) -> Unit = { },
    lastNameTextFieldValue: String,
    onLastNameTextFieldValueChanged: (String) -> Unit = { },
    phoneTextFieldValue: String,
    onPhoneTextFieldValueChanged: (String) -> Unit = { },
    addressTextFieldValue: String,
    onAddressTextFieldValueChanged: (String) -> Unit = { },
    paddingValues: PaddingValues = PaddingValues(),
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
                onValueChange = onNameTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_name_label)) },
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            OutlinedTextField(
                value = lastNameTextFieldValue,
                onValueChange = onLastNameTextFieldValueChanged,
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
                onValueChange = onPhoneTextFieldValueChanged,
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
                onValueChange = onAddressTextFieldValueChanged,
                label = { Text(stringResource(id = R.string.order_form_address_label)) },
            )
        }
    }
}