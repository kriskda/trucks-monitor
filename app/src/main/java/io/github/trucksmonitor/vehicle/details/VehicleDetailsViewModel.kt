package io.github.trucksmonitor.vehicle.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.domain.vehicle.usecase.GetVehicleDetails
import io.github.trucksmonitor.di.MainDispatcher
import io.github.trucksmonitor.vehicle.details.converter.toVehicleDetailsDisplayable
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsNavArgs
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsUiState
import io.github.trucksmonitor.vehicle.navArgs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val getVehicleDetails: GetVehicleDetails
) : ViewModel() {

    private val vehicleId = savedStateHandle.navArgs<VehicleDetailsNavArgs>().vehicleId

    private val _vehicleDetailsUiState: MutableStateFlow<VehicleDetailsUiState> = MutableStateFlow(VehicleDetailsUiState.Loading)
    val vehicleDetailsUiState: StateFlow<VehicleDetailsUiState> = _vehicleDetailsUiState

    private var vehicleUpdateJob: Job? = null

    fun scheduleVehicleUpdates() {
        stopVehicleUpdates()
        vehicleUpdateJob = viewModelScope.launch(coroutineDispatcher) {
            _vehicleDetailsUiState.value = VehicleDetailsUiState.Loading
            while (isActive) {
                runCatching {
                    getVehicleDetails(vehicleId)
                }.onFailure {
                    _vehicleDetailsUiState.value = VehicleDetailsUiState.Failure
                }.onSuccess {
                    _vehicleDetailsUiState.value = VehicleDetailsUiState.Loaded(
                        displayable = it.toVehicleDetailsDisplayable()
                    )
                }

                delay(UPDATE_PERIOD)
            }
        }
    }

    fun stopVehicleUpdates() {
        vehicleUpdateJob?.cancel()
    }

    companion object {
        private const val UPDATE_PERIOD = 5000L
    }
}