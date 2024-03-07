package io.github.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.domain.location.LocationRepositoryInterface
import io.github.domain.vehicle.VehicleRepositoryInterface
import io.github.repository.location.LocationRepository
import io.github.repository.vehicle.VehicleRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsVehicleRepository(repository: VehicleRepository): VehicleRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindsLocationRepository(repository: LocationRepository): LocationRepositoryInterface
}