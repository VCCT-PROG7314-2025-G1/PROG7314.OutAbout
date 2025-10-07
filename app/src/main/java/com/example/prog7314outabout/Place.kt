package com.example.prog7314outabout

data class Place(
    val name: String = "",
    val address: String = "",
    val imageUrl: String = "",  // optional thumbnail
    val category: String = ""   // e.g., Cafe, Restaurant, Bar
)