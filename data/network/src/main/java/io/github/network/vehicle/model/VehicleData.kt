package io.github.network.vehicle.model

import com.google.gson.annotations.SerializedName

data class VehicleData(
    @SerializedName("vehicleId") val vehicleId: String?
)