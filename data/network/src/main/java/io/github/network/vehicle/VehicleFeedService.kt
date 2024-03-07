package io.github.network.vehicle

import io.github.network.vehicle.model.VehicleData
import io.github.network.vehicle.model.VehicleDetailsData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// TODO Provide proper github auth token
private const val AUTH_TOKEN = ""

interface VehicleFeedService {

    @GET("dev/vehicles")
    suspend fun fetchVehicles(
        @Header("x-api-key") authToken: String = AUTH_TOKEN,
    ): List<VehicleData>

    @GET("dev/vehicles/{vehicleId}")
    suspend fun fetchVehicleDetails(
        @Header("x-api-key") authToken: String = AUTH_TOKEN,
        @Path("vehicleId") vehicleId: String
    ): VehicleDetailsData
}