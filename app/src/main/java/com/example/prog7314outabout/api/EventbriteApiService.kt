package com.example.prog7314outabout.api

import com.example.prog7314outabout.model.EventbriteEvent
import com.example.prog7314outabout.model.EventbriteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Retrofit service interface for Eventbrite API
 */
interface EventbriteApiService {
    
    @GET("events/search/")
    suspend fun searchEvents(
        @Header("Authorization") token: String,
        @Query("q") query: String? = null,
        @Query("location.address") location: String? = null,
        @Query("categories") categories: String? = null,
        @Query("start_date.range_start") startDate: String? = null,
        @Query("start_date.range_end") endDate: String? = null,
        @Query("page") page: Int = 1,
        @Query("expand") expand: String = "venue,organizer,category"
    ): Response<EventbriteResponse>
    
    @GET("events/{event_id}/")
    suspend fun getEventDetails(
        @Header("Authorization") token: String,
        @Query("event_id") eventId: String,
        @Query("expand") expand: String = "venue,organizer,category"
    ): Response<EventbriteEvent>
    
    @GET("categories/")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<EventbriteResponse>
    
    @GET("venues/{venue_id}/")
    suspend fun getVenueDetails(
        @Header("Authorization") token: String,
        @Query("venue_id") venueId: String
    ): Response<EventbriteEvent>
}
