//package com.example.prog7314outabout
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
//import com.google.android.libraries.places.api.net.PlacesClient
//class FoodDrinkFragment : Fragment() {
//
//    private lateinit var placesClient: PlacesClient
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var placesAdapter: PlacesAdapter
//    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_food_drink, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize Places SDK
//        if (!Places.isInitialized()) {
//            Places.initialize(requireContext(), getString(R.string.google_maps_key))
//        }
//        placesClient = Places.createClient(requireContext())
//
//        // Setup RecyclerView
//        recyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        placesAdapter = PlacesAdapter(emptyList()) { place ->
//            Toast.makeText(context, "Clicked: ${place.name}", Toast.LENGTH_SHORT).show()
//        }
//        recyclerView.adapter = placesAdapter
//
//        // Check permissions
//        checkLocationPermission()
//    }
//
//    private fun checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST_CODE
//            )
//        } else {
//            fetchNearbyFoodPlaces()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                fetchNearbyFoodPlaces()
//            } else {
//                Toast.makeText(
//                    context,
//                    "Location permission is required to fetch nearby places",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    private fun fetchNearbyFoodPlaces() {
//        try {
//            val request = FindCurrentPlaceRequest.newInstance(
//                listOf(Place.Field.NAME, Place.Field.LAT_LNG)
//            )
//
//            val placeResponse = placesClient.findCurrentPlace(request)
//            placeResponse.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val response = task.result
//                    val placeList = response?.placeLikelihoods?.map { it.place } ?: emptyList()
//                    placesAdapter.updatePlaces(placeList)
//                } else {
//                    val exception = task.exception
//                    Log.e("FoodDrinkFragment", "Place API failed: ${exception?.message}")
//                    Toast.makeText(context, "Failed to fetch places", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } catch (e: SecurityException) {
//            Log.e("FoodDrinkFragment", "Location permission missing: ${e.message}")
//        } catch (e: Exception) {
//            Log.e("FoodDrinkFragment", "Unexpected error: ${e.message}")
//        }
//    }
//}
//
//
//
////    // Handle permission result
////    override fun onRequestPermissionsResult(
////        requestCode: Int,
////        permissions: Array<out String>,
////        grantResults: IntArray
////    ) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
////            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
////                fetchNearbyFoodPlaces()
////            } else {
////                Toast.makeText(requireContext(), "Location permission is required to show nearby places", Toast.LENGTH_LONG).show()
////            }
////        }
////    }





package com.example.prog7314outabout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class FoodDrinkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placesClient: PlacesClient

    private val LOCATION_PERMISSION_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Places SDK if not already initialized
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.google_maps_key))

        }
        placesClient = Places.createClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_drink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        checkLocationPermissionAndLoad()
    }

    private fun checkLocationPermissionAndLoad() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        } else {
            // Permission granted, load places
            loadPlaces()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPlaces()
            } else {
                Toast.makeText(requireContext(), "Location permission required to show nearby places", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadPlaces() {
        val request = FindCurrentPlaceRequest.newInstance(
            listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.TYPES)
        )

        try {
            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    val foodPlaces = response?.placeLikelihoods?.filter { likelihood ->
                        likelihood.place.types?.contains(Place.Type.RESTAURANT) == true ||
                                likelihood.place.types?.contains(Place.Type.CAFE) == true ||
                                likelihood.place.types?.contains(Place.Type.BAR) == true
                    }?.map { likelihood ->
                        PlaceData(
                            name = likelihood.place.name ?: "",
                            address = likelihood.place.address ?: ""
                        )
                    } ?: emptyList()

                    recyclerView.adapter = PlacesAdapter() { selectedPlace ->
                        Toast.makeText(requireContext(), selectedPlace.name, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("FoodDrinkFragment", "Place API failed: ${task.exception}")
                }
            }
        } catch (se: SecurityException) {
            Log.e("FoodDrinkFragment", "Location permission missing: $se")
            Toast.makeText(requireContext(), "Location permission missing", Toast.LENGTH_SHORT).show()
        }
    }
}




