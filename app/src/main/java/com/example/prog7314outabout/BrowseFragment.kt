package com.example.prog7314outabout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView


class BrowseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Make sure this matches your designed layout file
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: wire the cards for navigation
        val cardActivities = view.findViewById<MaterialCardView>(R.id.card_activities)
        cardActivities.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AvailableActivitiesFragment())
                .addToBackStack(null)
                .commit()
        }


        val cardCategories = view.findViewById<MaterialCardView>(R.id.card_categories)
        cardCategories.setOnClickListener {
            // Later: navigate to CategoriesFragment
        }
    }
}
