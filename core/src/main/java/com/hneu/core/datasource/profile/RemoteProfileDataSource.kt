package com.hneu.core.datasource.profile

import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface RemoteProfileDataSource {
    fun loginByUsernameAndPassword(username: String, password: String): Flow<Result<User>>
    fun registerUser(user: User): Flow<Result<User>>
    fun changePassword(user: User): Flow<Result<User>>
    fun changeUserContacts(user: User): Flow<Result<User>>
    fun signOut(): Flow<Result<User>>
}