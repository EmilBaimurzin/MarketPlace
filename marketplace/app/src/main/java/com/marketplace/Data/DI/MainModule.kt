package com.marketplace.Data.DI

import com.marketplace.Common.networking.NetworkInterface
import com.marketplace.Data.Main.FragmentMainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Module
    object MainModule {
        @Provides
        fun provideRepository(networkInterface: NetworkInterface): FragmentMainRepository {
            return FragmentMainRepository(networkInterface)
        }
    }
}