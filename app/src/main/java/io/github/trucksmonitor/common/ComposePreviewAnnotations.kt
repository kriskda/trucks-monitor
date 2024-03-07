package io.github.trucksmonitor.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.trucksmonitor.R
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsScreenDisplayable
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsUiState
import io.github.trucksmonitor.vehicle.list.model.Vehicle
import io.github.trucksmonitor.vehicle.list.model.VehicleScreenDisplayable
import io.github.trucksmonitor.vehicle.list.model.VehicleScreenUiState

class VehicleScreenUiStateProvider : PreviewParameterProvider<VehicleScreenUiState> {
    override val values = sequenceOf(
        VehicleScreenUiState.Loaded(
            displayable = VehicleScreenDisplayable(
                nearbyVehiclesCount = 10,
                vehicles = listOf(
                    Vehicle(
                        vehicleId = "01",
                        distanceInfo = R.string.vehicle_distance_far_away
                    ),
                    Vehicle(
                        vehicleId = "02",
                        distanceInfo = R.string.vehicle_distance_near_by
                    ),
                )
            )
        ),
        VehicleScreenUiState.Loading,
        VehicleScreenUiState.Failure,
    )
}

class VehicleDetailsScreenUiStateProvider: PreviewParameterProvider<VehicleDetailsUiState>{
    override val values = sequenceOf(
        VehicleDetailsUiState.Loaded(
            displayable = VehicleDetailsScreenDisplayable(
                vehicleId = "01",
                coordinates = "N45°30`00\", E023°23`23\"",
                distanceInfo = R.string.vehicle_distance_far_away
            )
        ),
        VehicleDetailsUiState.Loading,
        VehicleDetailsUiState.Failure,
    )
}