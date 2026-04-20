package com.github.inlinefun.lazygo.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(
    application: Application
): AndroidViewModel(
    application
) {

    private val _isLocationProvider = MutableStateFlow(false)
    val isLocationProvider = _isLocationProvider.asStateFlow()

    fun hasNecessaryPermissions(): Boolean {
        return canAccessFineLocation() && isLocationProvider()
    }

    fun refreshProviderStatus() {
        _isLocationProvider.value = isLocationProvider();
    }

    private fun isLocationProvider(): Boolean {
        return try {
            val locationManager = getApplication<Application>()
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                true
            } catch (_: IllegalArgumentException) {
                true
            } catch (e: Exception) {
                Log.e(
                    "permissions",
                    "Application is not selected as the mock location provider",
                    e
                )
                false
            } finally {
                locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
            }
        } catch (_: Exception) {
            false
        }
    }

    private fun canAccessFineLocation(): Boolean {
        return ContextCompat
            .checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }

}