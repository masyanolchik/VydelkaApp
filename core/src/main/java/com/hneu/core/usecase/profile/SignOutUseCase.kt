package com.hneu.core.usecase.profile

import com.hneu.core.domain.user.User
import com.hneu.core.repository.profile.ProfileRepository

class SignOutUseCase(private val profileRepository: ProfileRepository) {
    operator fun invoke() = profileRepository.signOut()
}