package io.github.domain.location

import android.location.Location


interface LocationRepositoryInterface {

    fun getOperationBaseLocation() : Location
}