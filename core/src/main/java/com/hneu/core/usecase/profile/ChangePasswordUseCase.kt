package com.hneu.core.usecase.profile

import com.hneu.core.domain.user.User
import com.hneu.core.repository.profile.ProfileRepository

class ChangePasswordUseCase (private val profileRepository: ProfileRepository) {
    operator fun invoke(user: User) = profileRepository.changePassword(user)
}