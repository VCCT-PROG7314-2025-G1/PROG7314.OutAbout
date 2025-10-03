package com.example.prog7314outabout.model

import com.google.gson.annotations.SerializedName

/**
 * Data models for Eventbrite API responses
 */

data class EventbriteResponse(
    @SerializedName("events")
    val events: List<EventbriteEvent>? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null,
    @SerializedName("categories")
    val categories: List<Category>? = null
)

data class EventbriteEvent(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: Name? = null,
    @SerializedName("description")
    val description: Description? = null,
    @SerializedName("start")
    val start: DateTime? = null,
    @SerializedName("end")
    val end: DateTime? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("venue")
    val venue: Venue? = null,
    @SerializedName("organizer")
    val organizer: Organizer? = null,
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("logo")
    val logo: Logo? = null,
    @SerializedName("is_free")
    val isFree: Boolean = false,
    @SerializedName("is_online_event")
    val isOnlineEvent: Boolean = false
)

data class Name(
    @SerializedName("text")
    val text: String,
    @SerializedName("html")
    val html: String? = null
)

data class Description(
    @SerializedName("text")
    val text: String,
    @SerializedName("html")
    val html: String? = null
)

data class DateTime(
    @SerializedName("timezone")
    val timezone: String? = null,
    @SerializedName("local")
    val local: String? = null,
    @SerializedName("utc")
    val utc: String? = null
)

data class Venue(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("address")
    val address: Address? = null
)

data class Address(
    @SerializedName("address_1")
    val address1: String? = null,
    @SerializedName("address_2")
    val address2: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("longitude")
    val longitude: String? = null
)

data class Organizer(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null
)

data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("short_name")
    val shortName: String? = null
)

data class Logo(
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("crop_mask")
    val cropMask: CropMask? = null
)

data class CropMask(
    @SerializedName("top_left")
    val topLeft: Position? = null,
    @SerializedName("width")
    val width: Int? = null,
    @SerializedName("height")
    val height: Int? = null
)

data class Position(
    @SerializedName("x")
    val x: Int? = null,
    @SerializedName("y")
    val y: Int? = null
)

data class Pagination(
    @SerializedName("object_count")
    val objectCount: Int? = null,
    @SerializedName("page_number")
    val pageNumber: Int? = null,
    @SerializedName("page_size")
    val pageSize: Int? = null,
    @SerializedName("page_count")
    val pageCount: Int? = null,
    @SerializedName("has_more_items")
    val hasMoreItems: Boolean? = null
)
