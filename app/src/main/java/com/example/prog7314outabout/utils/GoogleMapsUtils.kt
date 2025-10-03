package com.example.prog7314outabout.utils

import android.content.Context
import com.example.prog7314outabout.config.ApiConfig
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Utility class for Google Maps operations
 */
object GoogleMapsUtils {
    
    /**
     * Move camera to Cape Town with default zoom level
     */
    fun moveToCapeTown(googleMap: GoogleMap) {
        val capeTown = LatLng(ApiConfig.DEFAULT_LATITUDE, ApiConfig.DEFAULT_LONGITUDE)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(capeTown, ApiConfig.DEFAULT_ZOOM_LEVEL)
        )
    }
    
    /**
     * Add a marker to the map
     */
    fun addMarker(
        googleMap: GoogleMap,
        position: LatLng,
        title: String,
        snippet: String? = null
    ) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet)
        )
    }
    
    /**
     * Add multiple markers to the map
     */
    fun addMarkers(
        googleMap: GoogleMap,
        markers: List<MapMarker>
    ) {
        markers.forEach { marker ->
            addMarker(googleMap, marker.position, marker.title, marker.snippet)
        }
    }
    
    /**
     * Check if Google Play Services are available
     */
    fun isGooglePlayServicesAvailable(context: Context): Boolean {
        return try {
            val googleApiAvailability = com.google.android.gms.common.GoogleApiAvailability.getInstance()
            val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
            resultCode == com.google.android.gms.common.ConnectionResult.SUCCESS
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * Data class for map markers
 */
data class MapMarker(
    val position: LatLng,
    val title: String,
    val snippet: String? = null
)
