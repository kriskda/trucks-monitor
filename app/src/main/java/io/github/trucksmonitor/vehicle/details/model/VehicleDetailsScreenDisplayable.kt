package io.github.trucksmonitor.vehicle.details.model

import androidx.annotation.StringRes

data class VehicleDetailsScreenDisplayable(
    val vehicleId: String,
    val coordinates: String,
    @StringRes val distanceInfo: Int,
)