package com.example.prog7314outabout.config

import android.content.Context
import com.example.prog7314outabout.R

/**
 * Configuration class for managing API keys and endpoints
 * This class provides a centralized way to access API configurations
 */
object ApiConfig {
    
    // Google Platform APIs
    const val GOOGLE_MAPS_API_KEY = "YOUR_GOOGLE_MAPS_API_KEY_HERE"
    const val GOOGLE_PLACES_API_KEY = "YOUR_GOOGLE_PLACES_API_KEY_HERE"
    
    // Eventbrite API
    const val EVENTBRITE_API_KEY = "YOUR_EVENTBRITE_API_KEY_HERE"
    const val EVENTBRITE_BASE_URL = "https://www.eventbriteapi.com/v3/"
    
    // API Endpoints
    const val EVENTBRITE_EVENTS_ENDPOINT = "events/"
    const val EVENTBRITE_VENUES_ENDPOINT = "venues/"
    const val EVENTBRITE_CATEGORIES_ENDPOINT = "categories/"
    
    // Google Maps Configuration
    const val DEFAULT_ZOOM_LEVEL = 15f
    const val DEFAULT_LATITUDE = -33.9249  // Cape Town latitude
    const val DEFAULT_LONGITUDE = 18.4241  // Cape Town longitude
    
    /**
     * Get API key from resources (more secure than hardcoding)
     */
    fun getGoogleMapsApiKey(context: Context): String {
        return try {
            context.getString(R.string.google_maps_api_key)
        } catch (e: Exception) {
            // Fallback to hardcoded key if resource is not found
            GOOGLE_MAPS_API_KEY
        }
    }
    
    fun getGooglePlacesApiKey(context: Context): String {
        return try {
            context.getString(R.string.google_places_api_key)
        } catch (e: Exception) {
            // Fallback to hardcoded key if resource is not found
            GOOGLE_PLACES_API_KEY
        }
    }
    
    fun getEventbriteApiKey(context: Context): String {
        return context.getString(R.string.eventbrite_api_key)
    }
    
    fun getEventbriteBaseUrl(context: Context): String {
        return context.getString(R.string.eventbrite_base_url)
    }
}
