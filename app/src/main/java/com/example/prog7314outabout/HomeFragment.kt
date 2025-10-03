package com.example.prog7314outabout

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Home Fragment with navigation and cards.
 */
class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dropdown icon tint
        val dropdownIcon = view.findViewById<ImageView>(R.id.ic_dropdown_icon)
        dropdownIcon?.setColorFilter(
            Color.parseColor("#436850"),
            android.graphics.PorterDuff.Mode.SRC_IN
        )

        // Bottom Navigation setup
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation?.let {
            it.selectedItemId = R.id.nav_home
            it.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> true
                    R.id.nav_search -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, BrowseFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    R.id.nav_favourites -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, FavouritesFragment())
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    else -> true
                }
            }
        }

        // Food & Drink button
        val foodDrinkButton = view.findViewById<MaterialButton>(R.id.food_drink_btn)
        foodDrinkButton?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FoodDrinkFragment())
                .addToBackStack(null)
                .commit()
        }

        // Things to do button
        val thingsToDoButton = view.findViewById<MaterialButton>(R.id.things_to_doBtn)
        thingsToDoButton?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ThingsToDoFragment())
                .addToBackStack(null)
                .commit()
        }

        // Shopping button
        val shoppingButton = view.findViewById<MaterialButton>(R.id.shopping_btn)
        shoppingButton?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShoppingFragment())
                .addToBackStack(null)
                .commit()
        }

        // Services button
        val servicesButton = view.findViewById<MaterialButton>(R.id.servicesBtn)
        servicesButton?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ServicesFragment())
                .addToBackStack(null)
                .commit()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
