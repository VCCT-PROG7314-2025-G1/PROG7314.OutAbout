package com.example.prog7314outabout.example

import android.content.Context
import com.example.prog7314outabout.repository.EventbriteRepository
import com.example.prog7314outabout.utils.GoogleMapsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Example class showing how to use the Google Platform API and Eventbrite integration
 */
class ApiUsageExample(private val context: Context) {
    
    private val eventbriteRepository = EventbriteRepository(context)
    
    /**
     * Example: Search for events in Cape Town
     */
    fun searchEventsExample() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = eventbriteRepository.searchEventsInCapeTown(
                query = "food",
                categories = "110", // Food & Drink category
                startDate = "2024-01-01T00:00:00Z",
                endDate = "2024-12-31T23:59:59Z"
            )
            
            result.onSuccess { response ->
                // Handle successful response
                response.events?.forEach { event ->
                    println("Event: ${event.name?.text}")
                    println("Date: ${event.start?.local}")
                    println("Venue: ${event.venue?.name}")
                    println("URL: ${event.url}")
                }
            }.onFailure { error ->
                // Handle error
                println("Error: ${error.message}")
            }
        }
    }
    
    /**
     * Example: Get event details
     */
    fun getEventDetailsExample(eventId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = eventbriteRepository.getEventDetails(eventId)
            
            result.onSuccess { event ->
                println("Event Details:")
                println("Name: ${event.name?.text}")
                println("Description: ${event.description?.text}")
                println("Start: ${event.start?.local}")
                println("End: ${event.end?.local}")
                println("Venue: ${event.venue?.name}")
                println("Address: ${event.venue?.address?.address1}")
                println("Is Free: ${event.isFree}")
            }.onFailure { error ->
                println("Error: ${error.message}")
            }
        }
    }
    
    /**
     * Example: Check Google Play Services availability
     */
    fun checkGooglePlayServices() {
        if (GoogleMapsUtils.isGooglePlayServicesAvailable(context)) {
            println("Google Play Services is available")
        } else {
            println("Google Play Services is not available")
        }
    }
    
    /**
     * Example: Get all categories
     */
    fun getCategoriesExample() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = eventbriteRepository.getCategories()
            
            result.onSuccess { response ->
                response.categories?.forEach { category ->
                    println("Category: ${category.name} (${category.shortName})")
                }
            }.onFailure { error ->
                println("Error: ${error.message}")
            }
        }
    }
}
