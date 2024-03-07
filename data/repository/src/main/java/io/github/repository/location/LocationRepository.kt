package io.github.repository.location

import android.location.Location
import io.github.domain.location.LocationRepositoryInterface
import javax.inject.Inject

class LocationRepository @Inject constructor() : LocationRepositoryInterface {

    override fun getOperationBaseLocation(): Location =
        Location("")
            .apply {
                latitude = 46.5223916
                longitude = 6.6314437
            }
}