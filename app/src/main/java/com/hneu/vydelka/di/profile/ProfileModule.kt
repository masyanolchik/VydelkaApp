package com.hneu.vydelka.di.profile

import com.hneu.core.datasource.profile.LocalProfileDataSource
import com.hneu.core.datasource.profile.RemoteProfileDataSource
import com.hneu.core.repository.profile.CoreProfileRepository
import com.hneu.core.repository.profile.ProfileRepository
import com.hneu.core.usecase.profile.ChangeContactsUseCase
import com.hneu.core.usecase.profile.ChangePasswordUseCase
import com.hneu.core.usecase.profile.LoginByUsernameAndPasswordUseCase
import com.hneu.core.usecase.profile.RegisterUserUseCase
import com.hneu.core.usecase.profile.SignOutUseCase
import com.hneu.vydelka.datasource.profile.RetrofitProfileDataSource
import com.hneu.vydelka.datasource.user.RoomUserDataSource
import com.hneu.vydelka.network.profile.AuthApiService
import com.hneu.vydelka.network.profile.ProfileApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface ProfileModule {
    @Binds
    fun bindLocalProfileDataSource(userDataSource: RoomUserDataSource): LocalProfileDataSource

    @Binds
    fun bindsRemoteProfileDataSource(retrofitProfileDataSource: RetrofitProfileDataSource): RemoteProfileDataSource

    companion object {
        @Provides
        fun provideChangeUserContactsUseCase(profileRepository: ProfileRepository): ChangeContactsUseCase {
            return ChangeContactsUseCase(profileRepository)
        }

        @Provides
        fun provideChangePasswordUseCase(profileRepository: ProfileRepository): ChangePasswordUseCase {
            return ChangePasswordUseCase(profileRepository)
        }

        @Provides
        fun provideRegisterUseCase(profileRepository: ProfileRepository): RegisterUserUseCase {
            return RegisterUserUseCase(profileRepository)
        }

        @Provides
        fun provideSignOutUseCase(profileRepository: ProfileRepository): SignOutUseCase {
            return SignOutUseCase(profileRepository)
        }

        @Provides
        fun provideLoginByUsernameAndPasswordUseCase(profileRepository: ProfileRepository): LoginByUsernameAndPasswordUseCase {
            return LoginByUsernameAndPasswordUseCase(profileRepository)
        }

        @Provides
        fun provideProfileRepository(localProfileDataSource: LocalProfileDataSource, remoteProfileDataSource: RemoteProfileDataSource) : ProfileRepository {
            return CoreProfileRepository(remoteProfileDataSource, localProfileDataSource)
        }

        @Provides
        fun provideProfileApiService(retrofit: Retrofit): ProfileApiService {
            return retrofit.create(ProfileApiService::class.java)
        }

        @Provides
        fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }
    }
}