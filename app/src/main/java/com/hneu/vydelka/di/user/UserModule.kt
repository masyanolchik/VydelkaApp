package com.hneu.vydelka.di.user

import com.hneu.core.datasource.user.LocalUserDataSource
import com.hneu.core.repository.user.CoreUserRepository
import com.hneu.core.repository.user.UserRepository
import com.hneu.core.usecase.user.GetByUsernameAndPasswordUseCase
import com.hneu.core.usecase.user.SaveUserUseCase
import com.hneu.vydelka.datasource.user.RoomUserDataSource
import com.hneu.vydelka.localdatabase.LocalDatabase
import com.hneu.vydelka.localdatabase.user.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserModule {

    @Binds
    fun bindLocalDataSource(roomUserDataSource: RoomUserDataSource): LocalUserDataSource

    companion object {

        @Provides
        fun provideGetByUsernameAndPasswordUseCase(userRepository: UserRepository): GetByUsernameAndPasswordUseCase {
            return GetByUsernameAndPasswordUseCase(userRepository)
        }

        @Provides
        fun provideSaveUserUseCase(userRepository: UserRepository): SaveUserUseCase {
            return SaveUserUseCase(userRepository)
        }

        @Provides
        fun provideUserRepository(localUserDataSource: LocalUserDataSource): UserRepository {
            return CoreUserRepository(localUserDataSource)
        }

        @Singleton
        @Provides
        fun provideUserDao(db: LocalDatabase): UserDao {
            return db.userDao()
        }
    }
}