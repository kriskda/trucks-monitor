package io.github.domain.vehicle.model

import android.location.Location

data class VehicleDetails(
    val vehicleId: String,
    val location: Location,
    val range: Range,
)

enum class Range{
    NEAR_BY,
    FAR_AWAY,
}