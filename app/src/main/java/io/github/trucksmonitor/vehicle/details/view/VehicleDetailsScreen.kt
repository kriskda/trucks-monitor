package io.github.trucksmonitor.vehicle.details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import io.github.trucksmonitor.R
import io.github.trucksmonitor.common.ObserveLifecycle
import io.github.trucksmonitor.common.ScreenTransitionAnimation
import io.github.trucksmonitor.common.VehicleDetailsScreenUiStateProvider
import io.github.trucksmonitor.vehicle.details.VehicleDetailsViewModel
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsNavArgs
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsScreenDisplayable
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsUiState

@Destination(
    style = ScreenTransitionAnimation::class,
    navArgsDelegate = VehicleDetailsNavArgs::class
)
@Composable
fun VehicleDetailsScreen(
    viewModel: VehicleDetailsViewModel = hiltViewModel()
) {
    ObserveLifecycle(
        onStart = { viewModel.scheduleVehicleUpdates() },
        onStop = { viewModel.stopVehicleUpdates() }
    )

    val uiState by viewModel.vehicleDetailsUiState.collectAsStateWithLifecycle()

    VehicleDetailsScreen(
        uiState = uiState
    )
}

@Composable
fun VehicleDetailsScreen(
    uiState: VehicleDetailsUiState
) {
    when (uiState) {
        is VehicleDetailsUiState.Loaded ->
            VehicleDetailsView(displayable = uiState.displayable)

        VehicleDetailsUiState.Loading -> LoadingView()
        VehicleDetailsUiState.Failure -> FailureView()
    }
}

@Composable
private fun VehicleDetailsView(
    displayable: VehicleDetailsScreenDisplayable
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.vehicle_details),
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(text = stringResource(R.string.vehicle_id, displayable.vehicleId))
        Text(text = displayable.coordinates)
        Text(text = stringResource(displayable.distanceInfo))
    }
}

@Composable
private fun LoadingView() {
    Text(text = stringResource(R.string.info_loading))
}

@Composable
private fun FailureView() {
    Text(text = stringResource(R.string.info_failure))
}

@Preview
@Composable
private fun VehicleDetailsScreenPreview(
    @PreviewParameter(VehicleDetailsScreenUiStateProvider::class) uiState: VehicleDetailsUiState
) {
    VehicleDetailsScreen(
        uiState = uiState
    )
}