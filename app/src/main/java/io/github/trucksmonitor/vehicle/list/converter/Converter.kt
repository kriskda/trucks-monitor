package io.github.trucksmonitor.vehicle.list.converter

import io.github.domain.vehicle.model.Range
import io.github.domain.vehicle.model.VehicleDetails
import io.github.trucksmonitor.R
import io.github.trucksmonitor.vehicle.list.model.Vehicle
import io.github.trucksmonitor.vehicle.list.model.VehicleScreenDisplayable

internal fun List<VehicleDetails>.toVehiclesDisplayable(): VehicleScreenDisplayable =
    VehicleScreenDisplayable(
        nearbyVehiclesCount = count { it.range == Range.NEAR_BY },
        vehicles = map {
            Vehicle(
                vehicleId = it.vehicleId,
                distanceInfo = when (it.range) {
                    Range.FAR_AWAY -> R.string.vehicle_distance_far_away
                    Range.NEAR_BY -> R.string.vehicle_distance_near_by
                }
            )
        }
    )