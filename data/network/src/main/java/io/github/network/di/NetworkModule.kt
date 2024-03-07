package io.github.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.network.vehicle.VehicleEndpoint
import io.github.network.vehicle.VehicleFeedService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideVehicleService(): VehicleFeedService =
        Retrofit.Builder()
            .baseUrl(VehicleEndpoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VehicleFeedService::class.java)
}