package io.github.trucksmonitor.vehicle.details.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.github.trucksmonitor.ui.theme.TrucksMonitorTheme
import io.github.trucksmonitor.vehicle.details.converter.toVehicleDetailsDisplayable
import io.github.trucksmonitor.vehicle.details.model.VehicleDetailsUiState
import io.github.trucksmonitor.vehicle.details.vehicleDetails
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VehicleDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays loading screen`() {
        composeTestRule.setContent {
            TrucksMonitorTheme {
                VehicleDetailsScreen(
                    uiState = VehicleDetailsUiState.Loading
                )
            }
        }
        composeTestRule.onNodeWithText("Loading…").assertIsDisplayed()
    }

    @Test
    fun `displays failure screen`() {
        composeTestRule.setContent {
            TrucksMonitorTheme {
                VehicleDetailsScreen(
                    uiState = VehicleDetailsUiState.Failure
                )
            }
        }
        composeTestRule.onNodeWithText("Failed loading content").assertIsDisplayed()
    }

    @Test
    fun `displays loaded screen`() {
        composeTestRule.setContent {
            TrucksMonitorTheme {
                VehicleDetailsScreen(
                    uiState = VehicleDetailsUiState.Loaded(
                        displayable = vehicleDetails().toVehicleDetailsDisplayable()
                    )
                )
            }
        }
        composeTestRule.onNodeWithText("Vehicle details").assertIsDisplayed()
        composeTestRule.onNodeWithText("ID vehicleId").assertIsDisplayed()
        composeTestRule.onNodeWithText("N00°00'00\", E012°00'00\"").assertIsDisplayed()
    }
}