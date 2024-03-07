package io.github.domain.vehicle.usecase

import io.github.domain.vehicle.model.Vehicle
import io.github.domain.vehicle.VehicleRepositoryInterface
import javax.inject.Inject

class GetVehicles @Inject constructor(
    private val vehicleRepository: VehicleRepositoryInterface
) {

    suspend operator fun invoke(): List<Vehicle> =
        vehicleRepository.getVehicles()
}