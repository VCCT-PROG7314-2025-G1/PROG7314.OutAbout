package com.example.prog7314outabout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the dropdown ImageView
        val dropdownIcon = view.findViewById<ImageView>(R.id.ic_dropdown_icon)

        // Apply the green tint (#436850)
        dropdownIcon.setColorFilter(
            Color.parseColor("#436850"),
            android.graphics.PorterDuff.Mode.SRC_IN
        )

        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_home
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_browse -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, BrowseFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_favourite -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, FavouritesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> true
            }
        }

        val foodDrinkButton = view.findViewById<MaterialButton>(R.id.food_drink_btn)
        foodDrinkButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, FoodDrinkFragment())
                .addToBackStack(null)
                .commit()
        }

        val shoppingButton = view.findViewById<MaterialButton>(R.id.shopping_btn)
        shoppingButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, ShoppingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}