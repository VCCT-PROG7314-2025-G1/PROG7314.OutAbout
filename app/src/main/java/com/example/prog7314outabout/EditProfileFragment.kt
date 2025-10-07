package com.example.prog7314outabout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog7314outabout.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: OutnAboutDBHelper
    private var currentUsername: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = OutnAboutDBHelper(requireContext())

        currentUsername = arguments?.getString("username") ?: ""

// Load user info
        val user = dbHelper.getUser(currentUsername)
        if (user != null) {
            binding.etFirstName.setText(user.name)
            binding.etLastName.setText(user.surname)
            binding.etUsername.setText(user.username)
            binding.etPassword.setText(user.password)
        }

        binding.btnSave.setOnClickListener {
            val updatedFirst = binding.etFirstName.text.toString().trim()
            val updatedLast = binding.etLastName.text.toString().trim()
            val updatedUsername = binding.etUsername.text.toString().trim()
            val updatedPassword = binding.etPassword.text.toString().trim()

            // Keep old values if left blank
            val finalFirst = if (updatedFirst.isNotEmpty()) updatedFirst else user?.name ?: ""
            val finalLast = if (updatedLast.isNotEmpty()) updatedLast else user?.surname ?: ""
            val finalUsername = if (updatedUsername.isNotEmpty()) updatedUsername else user?.username ?: ""
            val finalPassword = if (updatedPassword.isNotEmpty()) updatedPassword else user?.password ?: ""

            // Only check password if user typed a new one
            if (updatedPassword.isNotEmpty() && updatedPassword.length < 4) {
                Toast.makeText(requireContext(), "Password must be at least 4 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.updateUser(
                oldUsername = currentUsername,
                newFirst = finalFirst,
                newLast = finalLast,
                newUsername = finalUsername,
                newPassword = finalPassword
            )

            if (success) {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                currentUsername = finalUsername
                findNavController().navigateUp() // ✅ safe back navigation
            } else {
                Toast.makeText(requireContext(), "Update failed (username may already exist)", Toast.LENGTH_SHORT).show()
            }
        }

        // Bottom nav
        binding.bottomNavEdit.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_browse -> {
                    Toast.makeText(requireContext(), "Browse tapped", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_favourite -> {
                    Toast.makeText(requireContext(), "Favourites tapped", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_account -> true
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



//package com.example.prog7314outabout
//
//
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.prog7314outabout.databinding.FragmentEditProfileBinding
//
//
//class EditProfileFragment : Fragment() {
//    private var _binding: FragmentEditProfileBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // populate from SharedPrefs
//        binding.etFirstName.setText(SharedPreferences.getFirst(requireContext()))
//        binding.etLastName.setText(SharedPreferences.getLast(requireContext()))
//        binding.etUsername.setText(SharedPreferences.getUsername(requireContext()))
//        binding.etPassword.setText(SharedPreferences.getPassword(requireContext()))
//
//        binding.btnSave.setOnClickListener {
//            val first = binding.etFirstName.text.toString().trim()
//            val last = binding.etLastName.text.toString().trim()
//            val username = binding.etUsername.text.toString().trim()
//            val password = binding.etPassword.text.toString().trim()
//
//            if (first.isEmpty() || last.isEmpty() || username.isEmpty() || password.length < 4) {
//                Toast.makeText(requireContext(), "Please fill in all fields (password >= 4 chars)", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            SharedPreferences.saveUser(requireContext(), first, last, username, password)
//            Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show()
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }
//
//        // bottom nav
//        binding.bottomNavEdit.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_browse -> {
//                    Toast.makeText(requireContext(), "Search tapped", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_favourite -> {
//                    Toast.makeText(requireContext(), "Favourites tapped", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_account -> true
//                else -> false
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}