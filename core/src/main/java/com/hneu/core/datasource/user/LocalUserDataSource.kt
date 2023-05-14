package com.hneu.core.datasource.user

import com.hneu.core.domain.user.User
import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface LocalUserDataSource {
    fun saveUser(user: User): Flow<Result>
    fun getByUsernameAndPassword(username:String, password: String): Flow<Result>
}