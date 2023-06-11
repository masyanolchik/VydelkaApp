package com.hneu.core.usecase.profile

import com.hneu.core.repository.profile.ProfileRepository

class LoginByUsernameAndPasswordUseCase (private val profileRepository: ProfileRepository) {
    operator fun invoke(username: String, password: String) = profileRepository.loginByUsernameAndPassword(username, password)
}