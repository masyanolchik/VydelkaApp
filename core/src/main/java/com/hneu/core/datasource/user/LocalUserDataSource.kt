package com.hneu.core.datasource.user

import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun saveUser(user: User): Flow<Result<User>>
}