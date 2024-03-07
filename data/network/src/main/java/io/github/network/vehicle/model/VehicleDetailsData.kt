package io.github.network.vehicle.model

import com.google.gson.annotations.SerializedName

data class VehicleDetailsData(
    @SerializedName("vehicleId") val vehicleId: String?,
    @SerializedName("location") val locationData: LocationData?
)

data class LocationData(
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?
)
