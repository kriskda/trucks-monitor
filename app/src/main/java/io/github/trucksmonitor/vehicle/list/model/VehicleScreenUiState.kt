package io.github.trucksmonitor.vehicle.list.model

sealed interface VehicleScreenUiState {
    data object Loading : VehicleScreenUiState
    data class Loaded(val displayable: VehicleScreenDisplayable) : VehicleScreenUiState
    data object Failure : VehicleScreenUiState
}