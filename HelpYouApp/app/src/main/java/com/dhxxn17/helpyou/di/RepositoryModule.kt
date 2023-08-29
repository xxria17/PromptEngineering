package com.dhxxn17.helpyou.di

import com.dhxxn17.helpyou.data.repository.ChatRepository
import com.dhxxn17.helpyou.data.repository.ChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsChatRepository(
        repositoryImpl: ChatRepositoryImpl
    ): ChatRepository

}