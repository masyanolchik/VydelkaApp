package com.hneu.core.repository.profile

import com.hneu.core.datasource.profile.LocalProfileDataSource
import com.hneu.core.datasource.profile.RemoteProfileDataSource
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CoreProfileRepository(
    private val remoteProfileDataSource: RemoteProfileDataSource,
    private val localProfileDataSource: LocalProfileDataSource
) : ProfileRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loginByUsernameAndPassword(
        username: String,
        password: String
    ): Flow<Result<User>> {
        return remoteProfileDataSource.loginByUsernameAndPassword(username, password)
            .flatMapLatest {
                when(it) {
                    is Result.Success -> {
                        localProfileDataSource.saveUser(it.data)
                    }
                    else -> { flowOf(it) }
                }
            }
    }

    override fun registerUser(user: User): Flow<Result<User>> {
        return remoteProfileDataSource.registerUser(user)
    }

    override fun changePassword(user: User): Flow<Result<User>> {
        return remoteProfileDataSource.changePassword(user)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun changeContacts(user: User): Flow<Result<User>> {
        return remoteProfileDataSource.changeUserContacts(user)
            .flatMapLatest {
                when(it) {
                    is Result.Success -> {
                        localProfileDataSource.saveUser(it.data)
                    }
                    else -> { flowOf(it) }
                }
            }
    }

    override fun signOut(): Flow<Result<User>> {
        return remoteProfileDataSource.signOut()
    }
}