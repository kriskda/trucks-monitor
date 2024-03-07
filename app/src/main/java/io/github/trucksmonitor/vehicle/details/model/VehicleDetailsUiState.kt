package io.github.trucksmonitor.vehicle.details.model

sealed interface VehicleDetailsUiState {
    data object Loading : VehicleDetailsUiState
    data class Loaded(val displayable: VehicleDetailsScreenDisplayable) : VehicleDetailsUiState
    data object Failure : VehicleDetailsUiState
}