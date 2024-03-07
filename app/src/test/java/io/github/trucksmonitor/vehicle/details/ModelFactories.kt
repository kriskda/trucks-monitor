package io.github.trucksmonitor.vehicle.details

import android.location.Location
import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.VehicleDetails

fun vehicleDetails() = VehicleDetails(
    vehicleId = "vehicleId",
    location = location(),
    range = Range.NEAR_BY
)

fun location(
    latitude: Double = 34.0,
    longitude: Double = 12.0,
) = Location("").apply {
    this.longitude = latitude
    this.longitude = longitude
}