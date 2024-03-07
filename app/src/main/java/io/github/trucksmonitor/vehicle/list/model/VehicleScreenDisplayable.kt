package io.github.trucksmonitor.vehicle.list.model

import androidx.annotation.StringRes

data class VehicleScreenDisplayable(
    val nearbyVehiclesCount: Int,
    val vehicles: List<Vehicle>
)

data class Vehicle(
    val vehicleId: String,
    @StringRes val distanceInfo: Int,
)