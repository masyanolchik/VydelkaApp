package com.hneu.core.usecase.user

import com.hneu.core.repository.user.UserRepository

class GetByUsernameAndPasswordUseCase(private val userRepository: UserRepository) {
    operator fun invoke(username:String, password: String) = userRepository.getByUsernameAndPassword(username, password)
}