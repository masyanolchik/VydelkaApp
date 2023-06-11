package com.hneu.core.repository.profile

import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun loginByUsernameAndPassword(username: String, password: String): Flow<Result<User>>
    fun registerUser(user: User): Flow<Result<User>>
    fun changePassword(user: User): Flow<Result<User>>
    fun changeContacts(user: User): Flow<Result<User>>
    fun signOut(): Flow<Result<User>>
}