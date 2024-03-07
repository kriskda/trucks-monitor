package io.github.network

import io.github.network.vehicle.model.LocationData
import io.github.network.vehicle.model.VehicleData
import io.github.network.vehicle.model.VehicleDetailsData

fun vehicleData() =
    VehicleData(vehicleId = "vehicle1")

fun vehicleDetailsData() = VehicleDetailsData(
    vehicleId = "vehicle1",
    locationData = LocationData(latitude = "10.0", longitude = "20.0")
)