package io.github.domain.vehicle.usecase

import io.github.domain.location.LocationRepositoryInterface
import io.github.domain.vehicle.model.VehicleDetails
import io.github.domain.vehicle.VehicleRepositoryInterface
import javax.inject.Inject

class GetVehicleDetails @Inject constructor(
    private val locationRepository: LocationRepositoryInterface,
    private val vehicleRepository: VehicleRepositoryInterface
) {

    suspend operator fun invoke(vehicleId: String): VehicleDetails {
        val operationBaseLocation = locationRepository.getOperationBaseLocation()
        return vehicleRepository.getVehicleDetails(vehicleId, operationBaseLocation)
    }
}