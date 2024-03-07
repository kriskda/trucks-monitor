package io.github.network

import io.github.network.vehicle.VehicleFeedService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleFeedServiceTest {

    private val mockVehicleFeedService = mock<VehicleFeedService>()
    private val systemUnderTest = mockVehicleFeedService

    @Test
    fun `returns vehicle data`() = runTest {
        // Given
        val expectedVehicles = listOf(vehicleData())
        whenever(systemUnderTest.fetchVehicles()).thenReturn(expectedVehicles)

        // When
        val vehicles = systemUnderTest.fetchVehicles()

        // Then
        assert(vehicles == expectedVehicles)
    }

    @Test
    fun `returns vehicle details data`() = runTest {
        // Given
        val vehicleId = "vehicle1"
        whenever(systemUnderTest.fetchVehicleDetails(vehicleId = vehicleId)).thenReturn(vehicleDetailsData())

        // When
        val vehicleDetails = systemUnderTest.fetchVehicleDetails(vehicleId = vehicleId)

        // Then
        assert(vehicleDetails == vehicleDetailsData())
    }
}
