package io.github.repository.vehicle

import android.location.Location
import io.github.domain.vehicle.VehicleRepositoryInterface
import io.github.domain.vehicle.model.Vehicle
import io.github.domain.vehicle.model.VehicleDetails
import io.github.network.vehicle.VehicleFeedService
import io.github.repository.toVehicleDetailsDomain
import io.github.repository.toVehiclesDomain
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val vehicleFeedService: VehicleFeedService,
) : VehicleRepositoryInterface {

    override suspend fun getVehicles(): List<Vehicle> =
        vehicleFeedService.fetchVehicles()
            .toVehiclesDomain()

    override suspend fun getVehicleDetails(vehicleId: String, operationBaseLocation: Location): VehicleDetails =
        vehicleFeedService.fetchVehicleDetails(vehicleId = vehicleId)
            .toVehicleDetailsDomain(operationBaseLocation)
}