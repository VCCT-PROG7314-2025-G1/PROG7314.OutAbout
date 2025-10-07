package com.example.prog7314outabout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog7314outabout.databinding.FragmentAccountBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: OutnAboutDBHelper
    private var currentUsername: String = "" // Logged-in user

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = OutnAboutDBHelper(requireContext())

        currentUsername = arguments?.getString("username") ?: ""
        loadUserInfo(currentUsername)

//        // SharedPreferences for saving theme preference
//        val sharedPrefs = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
//        val isDarkMode = sharedPrefs.getBoolean("DarkMode", false)
//
//        // Set the switch state according to saved preference
//        binding.switchDarkMode.isChecked = isDarkMode
//        AppCompatDelegate.setDefaultNightMode(
//            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
//            else AppCompatDelegate.MODE_NIGHT_NO
//        )
//
//        // Handle switch toggle
//        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
//            with(sharedPrefs.edit()) {
//                putBoolean("DarkMode", isChecked)
//                apply()
//            }
//            AppCompatDelegate.setDefaultNightMode(
//                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
//                else AppCompatDelegate.MODE_NIGHT_NO
//            )
//        }


        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_home
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home ->  {
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
                R.id.nav_favourite -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, FavouritesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_account -> true
                else -> true
            }
        }


        // Spinners
        val countries = listOf("South Africa", "United States", "United Kingdom")
        val countryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = countryAdapter

        val languages = listOf("English", "Afrikaans", "Zulu")
        val langAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = langAdapter

        // Navigate to EditProfileFragment
        binding.editButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }
        // Logout
        binding.logoutButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, LandingFragment())
                .addToBackStack(null)
                .commit()
        }


        val sharedPref = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPref.getString("loggedInUsername", null)

        if (username != null) {
            loadUserInfo(username)
        } else {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
        }


    }

    private fun loadUserInfo(username: String) {
        val user = dbHelper.getUser(username)
        if (user != null) {
            binding.tvName.text = "${user.name} ${user.surname}"
            binding.tvUsername.text = user.username
        } else {
//            Toast.makeText(requireContext(), " found", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




