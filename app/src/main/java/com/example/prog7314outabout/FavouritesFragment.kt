package com.example.prog7314outabout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the BottomNavigationView safely now that the view exists
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_favourite

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
                R.id.nav_browse -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, BrowseFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_favourite -> true
                R.id.nav_account -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, AccountFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
