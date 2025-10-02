package com.example.prog7314outabout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupLocationDropdown(root: View) {
        val tvLocation = root.findViewById<TextView>(R.id.tv_location)
        val dropdownIcon = root.findViewById<ImageView>(R.id.ic_dropdown_icon)

        val cities = listOf("Cape Town", "Johannesburg", "Durban", "Pretoria", "Port Elizabeth")

        val clickListener = View.OnClickListener { anchor ->
            val popup = PopupMenu(requireContext(), anchor) // ✅ use requireContext() (safer inside fragment)
            cities.forEachIndexed { index, city ->
                popup.menu.add(0, index, index, city)
            }
            popup.setOnMenuItemClickListener { item ->
                tvLocation.text = item.title
                true
            }
            popup.show()
        }

        tvLocation.setOnClickListener(clickListener)
        dropdownIcon.setOnClickListener(clickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationDropdown(view)

        // Bottom navigation
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on home
                    true
                }
                R.id.nav_browse -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BrowseFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
        bottomNav.setItemIconTintList(null) // keeps original icon colors
        bottomNav.itemRippleColor = null // optional, removes ripple
        bottomNav.itemBackground = null // disables the Material3 selection background

    }
}
