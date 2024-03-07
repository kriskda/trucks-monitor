package io.github.trucksmonitor.vehicle.details

import androidx.lifecycle.SavedStateHandle
import io.github.domain.vehicle.usecase.GetVehicleDetails
import io.github.trucksmonitor.R
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class VehicleDetailsViewModelTest {

    private val vehicleId = "testVehicleId"
    private val savedStateHandle = SavedStateHandle(mapOf("vehicleId" to vehicleId))
    private val coroutineDispatcher = StandardTestDispatcher()
    private val mockGetVehicleDetails: GetVehicleDetails = mock {
        onBlocking { invoke(any()) } doReturn vehicleDetails()
    }

    private val systemUnderTest = VehicleDetailsViewModel(
        coroutineDispatcher = coroutineDispatcher,
        savedStateHandle = savedStateHandle,
        getVehicleDetails = mockGetVehicleDetails
    )

    @Test
    fun `updates vehicle and stops`() = runTest(coroutineDispatcher) {
        // Given
        systemUnderTest.scheduleVehicleUpdates()

        // When
        advanceTimeBy(5000L)
        systemUnderTest.stopVehicleUpdates()
        advanceUntilIdle()

        // Then
        verify(mockGetVehicleDetails, times(1)).invoke(any())
    }

    @Test
    fun `updates vehicle multiple times and stops`() = runTest(coroutineDispatcher) {
        // Given
        systemUnderTest.scheduleVehicleUpdates()

        // When
        advanceTimeBy(10000L)
        systemUnderTest.stopVehicleUpdates()
        advanceUntilIdle()

        // Then
        verify(mockGetVehicleDetails, times(2)).invoke(any())
    }

    @Test
    fun `emits failure when get vehicle details error`() = runTest(coroutineDispatcher) {
        // Given
        assumeGetVehicleDetailsError()
        systemUnderTest.scheduleVehicleUpdates()

        // When
        advanceTimeBy(5000L)
        systemUnderTest.stopVehicleUpdates()
        advanceUntilIdle()

        // Then
        val uiState = systemUnderTest.vehicleDetailsUiState.first()
        assert(uiState == VehicleDetailsUiState.Failure)
    }

    @Test
    fun `emits loaded when get vehicle details successful`() = runTest(coroutineDispatcher) {
        // Given
        assumeGetVehicleDetailsContent()
        systemUnderTest.scheduleVehicleUpdates()

        // When
        advanceTimeBy(5000L)
        systemUnderTest.stopVehicleUpdates()
        advanceUntilIdle()

        // Then
        val uiState = systemUnderTest.vehicleDetailsUiState.first()
        val displayable = (uiState as VehicleDetailsUiState.Loaded).displayable
        assert("vehicleId" == displayable.vehicleId)
        assert("N00°00'00\", E012°00'00\"" == displayable.coordinates)
        assert(R.string.vehicle_distance_near_by == displayable.distanceInfo)
    }

    private fun assumeGetVehicleDetailsContent() = runTest {
        whenever(mockGetVehicleDetails.invoke(any())).thenReturn(vehicleDetails())
    }

    private fun assumeGetVehicleDetailsError() = runTest {
        whenever(mockGetVehicleDetails.invoke(any())).thenThrow(RuntimeException("some error"))
    }
}