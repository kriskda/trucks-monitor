package io.github.trucksmonitor.vehicle.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.domain.vehicle.usecase.GetVehicleDetails
import io.github.domain.vehicle.usecase.GetVehicles
import io.github.domain.vehicle.model.Vehicle
import io.github.trucksmonitor.di.MainDispatcher
import io.github.trucksmonitor.vehicle.list.converter.toVehiclesDisplayable
import io.github.trucksmonitor.vehicle.list.model.VehicleScreenUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesScreenViewModel @Inject constructor(
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val getVehicles: GetVehicles,
    private val getVehicleDetails: GetVehicleDetails,
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<VehicleScreenUiState> = MutableStateFlow(VehicleScreenUiState.Loading)
    val uiStateFlow: StateFlow<VehicleScreenUiState> = _uiStateFlow

    private var vehicleUpdateJob: Job? = null

    fun loadVehiclesAndScheduleUpdates() {
        stopUpdates()
        viewModelScope.launch(coroutineDispatcher) {
            _uiStateFlow.value = VehicleScreenUiState.Loading
            runCatching {
                getVehicles()
            }.onFailure {
                _uiStateFlow.value = VehicleScreenUiState.Failure
            }.onSuccess {
                scheduleVehicleListUpdate(it)
            }
        }
    }

    fun stopUpdates() {
        vehicleUpdateJob?.cancel()
    }

    private fun scheduleVehicleListUpdate(vehicles: List<Vehicle>) {
        vehicleUpdateJob = viewModelScope.launch(coroutineDispatcher) {
            while (isActive) {
                val results = vehicles.map { vehicle ->
                    async {
                        runCatching { getVehicleDetails(vehicle.vehicleId) }.getOrNull()
                    }
                }.awaitAll()

                val displayable = results.filterNotNull().toVehiclesDisplayable()

                _uiStateFlow.value = VehicleScreenUiState.Loaded(displayable)

                delay(UPDATE_PERIOD)
            }
        }
    }

    companion object {
        private const val UPDATE_PERIOD = 5000L
    }
}