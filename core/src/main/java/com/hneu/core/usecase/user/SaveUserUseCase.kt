package com.hneu.core.usecase.user

import com.hneu.core.domain.user.User
import com.hneu.core.repository.user.UserRepository

class SaveUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: User) = userRepository.saveUser(user)
}