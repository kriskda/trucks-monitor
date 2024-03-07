package io.github.domain.vehicle.usecase

import io.github.domain.location.LocationRepositoryInterface
import io.github.domain.vehicle.VehicleRepositoryInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetVehicleDetailsTest {

    private val mockLocationRepository = mock<LocationRepositoryInterface>()
    private val mockVehicleRepository = mock<VehicleRepositoryInterface>()
    private val systemUnderTest = GetVehicleDetails(mockLocationRepository, mockVehicleRepository)

    @Test
    fun `returns vehicle details`() = runTest {
        // Given
        val vehicleId = "vehicle1"
        val operationBaseLocation = location()
        val location = location()
        val expectedVehicleDetails = vehicleDetails().copy(vehicleId = vehicleId, location = location)

        whenever(mockLocationRepository.getOperationBaseLocation()).thenReturn(operationBaseLocation)
        whenever(mockVehicleRepository.getVehicleDetails(vehicleId, operationBaseLocation)).thenReturn(expectedVehicleDetails)

        // When
        val vehicleDetails = systemUnderTest.invoke(vehicleId)

        // Then
        verify(mockLocationRepository).getOperationBaseLocation()
        verify(mockVehicleRepository).getVehicleDetails(vehicleId, operationBaseLocation)
        assert(vehicleDetails == expectedVehicleDetails)
    }
}
