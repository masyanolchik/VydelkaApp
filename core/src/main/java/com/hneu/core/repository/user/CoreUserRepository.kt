package com.hneu.core.repository.user

import com.hneu.core.datasource.user.LocalUserDataSource
import com.hneu.core.domain.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class CoreUserRepository(private val localDataSource: LocalUserDataSource) : UserRepository {
    override fun saveUser(user: User) = localDataSource.saveUser(user)

    override fun getByUsernameAndPassword(username: String, password: String) =
        localDataSource.getByUsernameAndPassword(username, password)
}