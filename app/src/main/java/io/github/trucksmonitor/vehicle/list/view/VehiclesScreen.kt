package io.github.trucksmonitor.vehicle.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.trucksmonitor.R
import io.github.trucksmonitor.common.ObserveLifecycle
import io.github.trucksmonitor.common.ScreenTransitionAnimation
import io.github.trucksmonitor.common.VehicleScreenUiStateProvider
import io.github.trucksmonitor.vehicle.destinations.VehicleDetailsScreenDestination
import io.github.trucksmonitor.vehicle.list.VehiclesScreenViewModel
import io.github.trucksmonitor.vehicle.list.model.Vehicle
import io.github.trucksmonitor.vehicle.list.model.VehicleScreenUiState

@RootNavGraph(start = true)
@Destination(style = ScreenTransitionAnimation::class)
@Composable
fun VehiclesScreen(
    navigator: DestinationsNavigator,
    viewModel: VehiclesScreenViewModel = hiltViewModel()
) {
    ObserveLifecycle(
        onStart = { viewModel.loadVehiclesAndScheduleUpdates() },
        onStop = { viewModel.stopUpdates() }
    )

    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    VehiclesScreen(
        listState = listState,
        uiState = uiState,
        onItemClick = { navigator.navigate(VehicleDetailsScreenDestination(it)) },
        onReloadAfterFailure = { viewModel.loadVehiclesAndScheduleUpdates() }
    )
}

@Composable
private fun VehiclesScreen(
    listState: LazyListState,
    uiState: VehicleScreenUiState,
    onItemClick: (String) -> Unit,
    onReloadAfterFailure: () -> Unit,
) {
    when (uiState) {
        is VehicleScreenUiState.Loaded -> {
            Column {
                InfoView(
                    nearbyVehiclesCount = uiState.displayable.nearbyVehiclesCount
                )
                VehicleListView(
                    listState = listState,
                    vehicles = uiState.displayable.vehicles,
                    onItemClick = onItemClick
                )
            }
        }

        VehicleScreenUiState.Loading -> LoadingView()
        VehicleScreenUiState.Failure -> FailureView(onReloadAfterFailure = onReloadAfterFailure)
    }
}

@Composable
private fun InfoView(
    nearbyVehiclesCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = stringResource(R.string.vehicle_nearby_count, nearbyVehiclesCount))
    }
}

@Composable
private fun VehicleListView(
    listState: LazyListState,
    vehicles: List<Vehicle>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {
        items(vehicles, key = { it.vehicleId }) {
            VehicleItemView(
                vehicle = it,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun VehicleItemView(
    vehicle: Vehicle,
    onItemClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier.clickable {
            onItemClick(vehicle.vehicleId)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.vehicle_id, vehicle.vehicleId),
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(vehicle.distanceInfo))
        }
    }
}

@Composable
private fun LoadingView() {
    Text(text = stringResource(R.string.info_loading))
}

@Composable
private fun FailureView(
    onReloadAfterFailure: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.info_failure))
            Button(onClick = { onReloadAfterFailure() }) {
                Text(text = stringResource(R.string.button_reload))
            }
        }
    }
}

@Preview
@Composable
private fun VehiclesScreenPreview(
    @PreviewParameter(VehicleScreenUiStateProvider::class) uiState: VehicleScreenUiState,
) {
    VehiclesScreen(
        listState = LazyListState(),
        uiState = uiState,
        onItemClick = {},
        onReloadAfterFailure = {}
    )
}