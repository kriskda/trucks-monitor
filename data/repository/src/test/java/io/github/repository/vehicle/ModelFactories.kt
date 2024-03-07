package io.github.repository.vehicle

import android.location.Location
import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.VehicleDetails
import io.github.network.vehicle.model.LocationData
import io.github.network.vehicle.model.VehicleData
import io.github.network.vehicle.model.VehicleDetailsData

fun vehicleData() =
    VehicleData(vehicleId = "vehicle1")

fun location(
    latitude: Double = 34.0,
    longitude: Double = 12.0,
) = Location("").apply {
    this.longitude = latitude
    this.longitude = longitude
}

fun vehicleDetails() =
    VehicleDetails(
        "vehicle_id",
        location(),
        Range.NEAR_BY
    )

fun vehicleDetailsData() = VehicleDetailsData(
    vehicleId = "vehicle1",
    locationData = LocationData(latitude = "10.0", longitude = "20.0")
)