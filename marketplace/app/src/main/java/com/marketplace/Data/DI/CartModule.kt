package com.marketplace.Data.DI

import com.marketplace.Common.networking.NetworkInterface
import com.marketplace.Data.Cart.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartRepository(networkInterface: NetworkInterface): CartRepository {
        return CartRepository(networkInterface)
    }
}