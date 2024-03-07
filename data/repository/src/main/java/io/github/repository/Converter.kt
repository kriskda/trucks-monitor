package io.github.repository

import android.location.Location
import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.Vehicle
import io.github.domain.vehicle.model.VehicleDetails
import io.github.network.vehicle.model.LocationData
import io.github.network.vehicle.model.VehicleData
import io.github.network.vehicle.model.VehicleDetailsData

internal fun List<VehicleData>.toVehiclesDomain() =
    mapNotNull { vehicleData ->
        vehicleData.vehicleId?.let {
            Vehicle(vehicleId = it)
        }
    }

internal fun VehicleDetailsData.toVehicleDetailsDomain(
    operationBaseLocation: Location
): VehicleDetails {
    val location = locationData?.toLocation() ?: emptyLocation()
    return VehicleDetails(
        vehicleId = vehicleId ?: "empty_id",
        location = location,
        range = calculateRange(location, operationBaseLocation)
    )
}

private fun emptyLocation() =
    Location("").apply {
        latitude = 0.0
        longitude = 0.0
    }

private fun LocationData.toLocation(): Location =
    Location("").apply {
        latitude = this@toLocation.latitude?.toSafeDouble() ?: 0.0
        longitude = this@toLocation.longitude?.toSafeDouble() ?: 0.0
    }

private fun String.toSafeDouble(): Double =
    runCatching { toDouble() }.getOrDefault(0.0)

private fun calculateRange(
    targetLocation: Location,
    originLocation: Location
): Range {
    val distanceInMeters = targetLocation.distanceTo(originLocation)
    return if (distanceInMeters < 1000) Range.NEAR_BY else Range.FAR_AWAY
}