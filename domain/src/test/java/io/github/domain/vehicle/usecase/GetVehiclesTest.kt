package io.github.domain.vehicle.usecase

import io.github.domain.vehicle.VehicleRepositoryInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetVehiclesTest {

    private val mockVehicleRepository = mock<VehicleRepositoryInterface>()
    private val systemUnderTest = GetVehicles(mockVehicleRepository)

    @Test
    fun `returns list of vehicles`() = runTest {
        // Given
        val expectedVehicles = (0..1).map { vehicle().copy(vehicleId = "%it") }
        whenever(mockVehicleRepository.getVehicles()).thenReturn(expectedVehicles)

        // When
        val vehicles = systemUnderTest.invoke()

        // Then
        verify(mockVehicleRepository).getVehicles()
        assert(vehicles == expectedVehicles)
    }
}
