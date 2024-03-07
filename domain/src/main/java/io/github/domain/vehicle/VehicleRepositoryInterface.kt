package io.github.domain.vehicle

import android.location.Location
import io.github.domain.vehicle.model.Vehicle
import io.github.domain.vehicle.model.VehicleDetails

interface VehicleRepositoryInterface {

    suspend fun getVehicles(): List<Vehicle>

    suspend fun getVehicleDetails(vehicleId: String, operationBaseLocation: Location): VehicleDetails
}