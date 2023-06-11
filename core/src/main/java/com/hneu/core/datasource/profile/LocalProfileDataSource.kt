package com.hneu.core.datasource.profile

import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface LocalProfileDataSource {
    fun getByUsername(username: String): Flow<Result<User>>
    fun saveUser(user: User): Flow<Result<User>>
}