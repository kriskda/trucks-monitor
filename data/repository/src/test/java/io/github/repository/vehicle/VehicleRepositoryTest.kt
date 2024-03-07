package io.github.repository.vehicle

import io.github.domain.vehicle.model.Range
import io.github.network.vehicle.VehicleFeedService
import io.github.network.vehicle.model.LocationData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleRepositoryTest {

    private val mockVehicleFeedService = mock<VehicleFeedService>()
    private val systemUnderTest = VehicleRepository(mockVehicleFeedService)

    @Test
    fun `fetchVehicles and returns domain vehicles`() = runTest {
        // Given
        val vehicleDataList = (0..1).map { vehicleData().copy(vehicleId = "$it") }
        whenever(mockVehicleFeedService.fetchVehicles()).thenReturn(vehicleDataList)

        // When
        val vehicles = systemUnderTest.getVehicles()

        // Then
        verify(mockVehicleFeedService).fetchVehicles()
        assert(vehicles[0].vehicleId == "0")
        assert(vehicles[1].vehicleId == "1")
        assert(vehicles.size == 2)
    }

    @Test
    fun `fetchVehicles and returns domain vehicles when vehicleId is null`() = runTest {
        // Given
        val vehicleDataList = listOf(
            vehicleData().copy(vehicleId = "0"),
            vehicleData().copy(vehicleId = null)
        )
        whenever(mockVehicleFeedService.fetchVehicles()).thenReturn(vehicleDataList)

        // When
        val vehicles = systemUnderTest.getVehicles()

        // Then
        verify(mockVehicleFeedService).fetchVehicles()
        assert(vehicles[0].vehicleId == "0")
        assert(vehicles.size == 1)
    }

    @Test
    fun `fetchVehicleDetails and returns domain vehicle details`() = runTest {
        // Given
        val vehicleId = "vehicle1"
        val operationBaseLocation = location()
        val vehicleDetailsData = vehicleDetailsData().copy(vehicleId = vehicleId)
        whenever(mockVehicleFeedService.fetchVehicleDetails(vehicleId = vehicleId)).thenReturn(vehicleDetailsData)

        // When
        val vehicleDetails = systemUnderTest.getVehicleDetails(vehicleId, operationBaseLocation)

        // Then
        verify(mockVehicleFeedService).fetchVehicleDetails(vehicleId = vehicleId)
        assert(vehicleDetails.vehicleId == vehicleId)
        assert(vehicleDetails.range == Range.FAR_AWAY)
        assertEquals("Latitude should match", 10.0, vehicleDetails.location.latitude, 0.00001)
        assertEquals("Latitude should match", 20.0, vehicleDetails.location.longitude, 0.00001)
    }

    @Test
    fun `fetchVehicleDetails and returns domain vehicle details with range far away when vehicle is more than 1km`() = runTest {
        // Given
        val vehicleId = "vehicle1"
        val operationBaseLocation = location(latitude = 0.0, longitude = 0.0)
        val vehicleDetailsData = vehicleDetailsData()
            .copy(vehicleId = vehicleId, LocationData("0.0091", "0.0"))
        whenever(mockVehicleFeedService.fetchVehicleDetails(vehicleId = vehicleId)).thenReturn(vehicleDetailsData)

        // When
        val vehicleDetails = systemUnderTest.getVehicleDetails(vehicleId, operationBaseLocation)

        // Then
        assert(vehicleDetails.range == Range.FAR_AWAY)
    }

    @Test
    fun `fetchVehicleDetails and returns domain vehicle details with range near by when vehicle is less than 1km`() = runTest {
        // Given
        val vehicleId = "vehicle1"
        val operationBaseLocation = location(latitude = 0.0, longitude = 0.0)
        val vehicleDetailsData = vehicleDetailsData()
            .copy(vehicleId = vehicleId, LocationData("0.009", "0.0"))
        whenever(mockVehicleFeedService.fetchVehicleDetails(vehicleId = vehicleId)).thenReturn(vehicleDetailsData)

        // When
        val vehicleDetails = systemUnderTest.getVehicleDetails(vehicleId, operationBaseLocation)

        // Then
        assert(vehicleDetails.range == Range.NEAR_BY)
    }
}
