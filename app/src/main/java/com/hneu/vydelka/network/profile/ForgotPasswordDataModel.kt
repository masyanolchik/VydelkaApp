package com.hneu.vydelka.network.profile

data class ForgotPasswordDataModel(
    val username: String,
    val email: String,
    val newPassword: String,
)
