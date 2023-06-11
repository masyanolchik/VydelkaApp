package com.hneu.vydelka.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hneu.core.domain.user.User
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.accountmanager.AccountManager
import com.hneu.vydelka.accountmanager.AccountManagerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val accountManager: AccountManager) : ViewModel() {

    private val _userRegisterState = MutableStateFlow<Result<User>>(Result.Loading())
    val userRegisterState: StateFlow<Result<User>> = _userRegisterState

    private val _passwordChangedState = MutableStateFlow<Result<User>>(Result.Loading())
    val passwordChangedState: StateFlow<Result<User>> = _passwordChangedState

    private val _currentUser = MutableStateFlow<Result<User>>(Result.Success(AccountManagerImpl.UNREGISTERED_USER))
    val currentUser: StateFlow<Result<User>> = _currentUser

    fun loginWithUsernameAndPassword(username: String, password: String) {
        viewModelScope.launch {
            _currentUser.emit(Result.Loading())
            accountManager.loginByUsernameAndPassword(username, password)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _currentUser.emit(it)
                }
        }
    }

    fun registerUser(username: String, email: String, password: String, name:String, lastName: String, phone: String, address: String) {
        viewModelScope.launch {
            _userRegisterState.emit(Result.Loading())
            accountManager.register(
                User(
                    username = username,
                    email = email,
                    password = password,
                    name = name,
                    lastName = lastName,
                    phoneNumber = phone,
                    shippingAddress = address,
                )
            )
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _userRegisterState.emit(it)
                }
        }
    }

    fun changePassword(username:String, email: String, password: String) {
        viewModelScope.launch {
            _passwordChangedState.emit(Result.Loading())
            accountManager.changePassword(
                User(
                    username = username,
                    email = email,
                    password = password,
                )
            )
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _passwordChangedState.emit(it)
                }
        }
    }

    fun changeContacts(name: String, lastName: String, phone: String, address: String) {
        viewModelScope.launch {
            val currentUserState = _currentUser.value
            _currentUser.emit(Result.Loading())
            if(currentUserState is Result.Success) {
                val updatedUser = User(
                    id = currentUserState.data.id,
                    username = currentUserState.data.username,
                    email = currentUserState.data.email,
                    name = name,
                    lastName = lastName,
                    phoneNumber = phone,
                    shippingAddress = address,
                )
                accountManager.changeContacts(updatedUser)
                    .flowOn(Dispatchers.IO)
                    .collectLatest {
                        _currentUser.emit(it)
                    }

            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountManager.signOut()
                .collectLatest {
                    _currentUser.emit(it)
                }
        }
    }
}