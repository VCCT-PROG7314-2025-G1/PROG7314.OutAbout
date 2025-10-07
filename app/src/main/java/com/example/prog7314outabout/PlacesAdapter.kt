package com.example.prog7314outabout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesAdapter(
    private var places: List<com.google.android.libraries.places.api.model.Place> = emptyList(),
    private val onClick: (com.google.android.libraries.places.api.model.Place) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.placeNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.nameTextView.text = place.name ?: "Unknown"
        holder.itemView.setOnClickListener { onClick(place) }
    }

    override fun getItemCount(): Int = places.size

    fun updatePlaces(newPlaces: List<com.google.android.libraries.places.api.model.Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}


