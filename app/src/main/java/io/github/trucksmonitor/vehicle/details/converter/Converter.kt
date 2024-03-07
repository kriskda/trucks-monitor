package io.github.trucksmonitor.vehicle.details.converter

import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.VehicleDetails
import io.github.trucksmonitor.R
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsScreenDisplayable
import kotlin.math.abs
import kotlin.math.roundToInt

internal fun VehicleDetails.toVehicleDetailsDisplayable() =
    VehicleDetailsScreenDisplayable(
        vehicleId = vehicleId,
        coordinates = "${location.latitude.toLatitudeStringDMS()}, ${location.longitude.toLongitudeStringDMS()}",
        distanceInfo = when (range) {
            Range.FAR_AWAY -> R.string.vehicle_distance_far_away
            Range.NEAR_BY -> R.string.vehicle_distance_near_by
        }
    )

internal fun Double.toLatitudeStringDMS(): String {
    val (degrees, minutes, seconds) = abs(this).toDegreeMinuteSecond()
    val direction = if (this >= 0) "N" else "S"
    return "$direction%02d°$minutes'$seconds\"".format(degrees.toInt())
}

internal fun Double.toLongitudeStringDMS(): String {
    val (degrees, minutes, seconds) = abs(this).toDegreeMinuteSecond()
    val direction = if (this >= 0) "E" else "W"
    return "$direction%03d°$minutes'$seconds\"".format(degrees.toInt())
}

private fun Double.toDegreeMinuteSecond(): Triple<String, String, String> {
    val degrees = this.toInt()
    val minutes = ((this - degrees) * 60).toInt()
    val seconds = (((this - degrees) * 60 - minutes) * 60).roundToInt()
    return Triple(
        degrees.toString(),
        "%02d".format(minutes),
        "%02d".format(seconds)
    )
}