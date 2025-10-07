package com.example.prog7314outabout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class BrowseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_browse
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_browse -> true
                R.id.nav_favourite -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, FavouritesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_account -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, AccountFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> true
            }
        }
        // ✅ Browse by Category card
        val browseCategoryButton = view.findViewById<MaterialButton>(R.id.browscategorybtn)
        browseCategoryButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, BrowseByCategoryFragment())
                .addToBackStack(null)
                .commit()
        }

        // ✅ Available Activities card
        val activitiesCard = view.findViewById<MaterialCardView>(R.id.card_activities)
        activitiesCard?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AvailableActivitiesFragment())
                .addToBackStack(null)
                .commit()
        }
    }



}