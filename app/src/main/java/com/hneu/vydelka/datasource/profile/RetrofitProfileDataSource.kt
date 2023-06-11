package com.hneu.vydelka.datasource.profile

import com.hneu.core.datasource.profile.RemoteProfileDataSource
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.network.profile.AuthApiService
import com.hneu.vydelka.network.profile.LoginDataModel
import com.hneu.vydelka.network.profile.ProfileApiService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitProfileDataSource @Inject constructor(private val authApiService: AuthApiService, private val profileApiService: ProfileApiService): RemoteProfileDataSource {
    override fun loginByUsernameAndPassword(
        username: String,
        password: String
    ): Flow<Result<User>> {
        return callbackFlow {
            val user = LoginDataModel(username, password)
            val call: Call<User> = authApiService.loginUser(user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }
            })
            awaitClose()
        }
    }

    override fun registerUser(user: User): Flow<Result<User>> {
        return callbackFlow {
            val call = authApiService.register(user)
            call.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        trySendBlocking(Result.Completed())
                    } else {
                        trySendBlocking(Result.Error(IllegalStateException(response.errorBody().toString())))
                    }
                    close()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }

    override fun changePassword(user: User): Flow<Result<User>> {
        return callbackFlow {
            val call = authApiService.changePassword(user)
            call.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        trySendBlocking(Result.Completed())
                    } else {
                        trySendBlocking(Result.Error(IllegalStateException(response.errorBody().toString())))
                    }
                    close()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }

            })
            awaitClose()
        }
    }

    override fun changeUserContacts(user: User): Flow<Result<User>> {
        return callbackFlow {
            val call: Call<User> = profileApiService.changeContacts(user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trySendBlocking(Result.Success(it))
                        }
                    } else {
                        response.errorBody()?.let {
                            trySendBlocking(Result.Error(IllegalStateException(it.string())))
                        }
                    }
                    close()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }
            })
            awaitClose()
        }
    }

    override fun signOut(): Flow<Result<User>> {
        return callbackFlow {
            val call: Call<Void> = authApiService.signOut()
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful()) {
                        trySendBlocking(Result.Completed())
                    } else {
                        trySendBlocking(Result.Error(IllegalStateException(response.message())))
                    }
                    close()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    trySendBlocking(Result.Error(t))
                    close()
                }
            })
            awaitClose()
        }
    }
}