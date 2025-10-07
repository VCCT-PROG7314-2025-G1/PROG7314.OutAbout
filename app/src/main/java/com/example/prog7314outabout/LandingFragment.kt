package com.example.prog7314outabout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
class LandingFragment : Fragment(R.layout.fragment_landing) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val loginButton: Button = view.findViewById(R.id.loginButton)
        val registerButton: Button = view.findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            // Safe navigation
            navController.currentDestination?.let { dest ->
                if (dest.id == R.id.landingFragment) {
                    navController.navigate(R.id.action_landingFragment_to_loginFragment)
                }
            }
        }

        registerButton.setOnClickListener {
            navController.currentDestination?.let { dest ->
                if (dest.id == R.id.landingFragment) {
                    navController.navigate(R.id.action_landingFragment_to_registerFragment)
                }
            }
        }
    }
}