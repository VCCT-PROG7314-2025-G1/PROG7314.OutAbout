package com.example.prog7314outabout.repository

import android.content.Context
import com.example.prog7314outabout.api.EventbriteApiService
import com.example.prog7314outabout.config.ApiConfig
import com.example.prog7314outabout.model.EventbriteEvent
import com.example.prog7314outabout.model.EventbriteResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Repository class for handling Eventbrite API calls
 */
class EventbriteRepository(private val context: Context) {
    
    private val apiService: EventbriteApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.getEventbriteBaseUrl(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create()
    }
    
    private val authToken: String by lazy {
        "Bearer ${ApiConfig.getEventbriteApiKey(context)}"
    }
    
    /**
     * Search for events in Cape Town
     */
    suspend fun searchEventsInCapeTown(
        query: String? = null,
        categories: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        page: Int = 1
    ): Result<EventbriteResponse> {
        return try {
            val response = apiService.searchEvents(
                token = authToken,
                query = query,
                location = "Cape Town, South Africa",
                categories = categories,
                startDate = startDate,
                endDate = endDate,
                page = page
            )
            
            if (response.isSuccessful) {
                Result.success(response.body() ?: EventbriteResponse())
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get event details by ID
     */
    suspend fun getEventDetails(eventId: String): Result<EventbriteEvent> {
        return try {
            val response = apiService.getEventDetails(
                token = authToken,
                eventId = eventId
            )
            
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("Event not found"))
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get all categories
     */
    suspend fun getCategories(): Result<EventbriteResponse> {
        return try {
            val response = apiService.getCategories(token = authToken)
            
            if (response.isSuccessful) {
                Result.success(response.body() ?: EventbriteResponse())
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get venue details by ID
     */
    suspend fun getVenueDetails(venueId: String): Result<EventbriteEvent> {
        return try {
            val response = apiService.getVenueDetails(
                token = authToken,
                venueId = venueId
            )
            
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("Venue not found"))
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
