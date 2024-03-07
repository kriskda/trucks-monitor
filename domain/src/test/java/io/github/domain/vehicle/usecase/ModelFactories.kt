package io.github.domain.vehicle.usecase

import android.location.Location
import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.Vehicle
import io.github.domain.vehicle.model.VehicleDetails

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

fun vehicle() =
    Vehicle("vehicle_id")