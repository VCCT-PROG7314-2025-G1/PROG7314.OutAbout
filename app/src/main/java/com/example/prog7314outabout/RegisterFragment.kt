package com.example.prog7314outabout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var dbHelper: OutnAboutDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseApp.initializeApp(requireContext())
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Link UI
        nameEditText = view.findViewById(R.id.name_txtbox)
        surnameEditText = view.findViewById(R.id.surname_txtbox)
        numberEditText = view.findViewById(R.id.number_txtbox)
        emailEditText = view.findViewById(R.id.email_txtbox)
        countryEditText = view.findViewById(R.id.country_txtbox)
        usernameEditText = view.findViewById(R.id.usernameInput)
        passwordEditText = view.findViewById(R.id.passwordInput)
        registerButton = view.findViewById(R.id.profileButton)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        dbHelper = OutnAboutDBHelper(requireContext())

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()
            val number = numberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val country = countryEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (name.isEmpty() || surname.isEmpty() || number.isEmpty() ||
                email.isEmpty() || country.isEmpty() || username.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.userExists(username)) {
                Toast.makeText(requireContext(), "User already exists locally", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase registration
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                        val userMap = hashMapOf(
                            "name" to name,
                            "surname" to surname,
                            "mobile_number" to number,
                            "country" to country,
                            "username" to username,
                            "email" to email
                        )

                        db.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Registered successfully!", Toast.LENGTH_SHORT).show()
                                dbHelper.insertUser(name, surname, number, email, country, username, password)
                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Failed to save user data: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Register in Firestore
                                } else {
                                    if (task.exception is FirebaseAuthUserCollisionException) {
                                        Toast.makeText(context, "Email already exists. Try logging in.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                    }
                }
        }

//        val firebaseAppCheck = FirebaseAppCheck.getInstance()
//        firebaseAppCheck.installAppCheckProviderFactory(
//            DebugAppCheckProviderFactory.getInstance()
//        )
    }
}




//package com.example.prog7314outabout
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.FirebaseApp
//
//
//
//class RegisterFragment : Fragment() {
//
//    private lateinit var nameEditText: EditText
//    private lateinit var surnameEditText: EditText
//    private lateinit var numberEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var countryEditText: EditText
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var registerButton: Button
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var db: FirebaseFirestore
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        FirebaseApp.initializeApp(requireContext())
//        return inflater.inflate(R.layout.fragment_register, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Link UI components
//        nameEditText = view.findViewById(R.id.name_txtbox)
//        surnameEditText = view.findViewById(R.id.surname_txtbox)
//        numberEditText = view.findViewById(R.id.number_txtbox)
//        emailEditText = view.findViewById(R.id.email_txtbox)
//        countryEditText = view.findViewById(R.id.country_txtbox)
//        usernameEditText = view.findViewById(R.id.usernameInput)
//        passwordEditText = view.findViewById(R.id.passwordInput)
//        registerButton = view.findViewById(R.id.profileButton)
//
//        // Initialize Firebase Auth & Firestore
//        auth = FirebaseAuth.getInstance()
//        db = FirebaseFirestore.getInstance()
//
//        val dbHelper = OutnAboutDBHelper(requireContext())
//
//        registerButton.setOnClickListener {
//            val name = nameEditText.text.toString().trim()
//            val surname = surnameEditText.text.toString().trim()
//            val number = numberEditText.text.toString().trim()
//            val email = emailEditText.text.toString().trim()
//            val country = countryEditText.text.toString().trim()
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString()
//
//            // Validate fields
//            if (name.isEmpty() || surname.isEmpty() || number.isEmpty() ||
//                email.isEmpty() || country.isEmpty() || username.isEmpty() || password.isEmpty()
//            ) {
//                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Check if user exists locally
//            if (dbHelper.userExists(username)) {
//                Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Register with Firebase Auth (Email/Password)
//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val firebaseUser = auth.currentUser
//                        val uid = firebaseUser?.uid
//
//                        // Save extra info in Firestore under UID
//                        if (uid != null) {
//                            val userMap = hashMapOf(
//                                "name" to name,
//                                "surname" to surname,
//                                "mobile_number" to number,
//                                "country" to country,
//                                "username" to username,
//                                "email" to email
//                            )
//
//                            db.collection("users").document(uid)
//                                .set(userMap)
//                                .addOnSuccessListener {
//                                    Toast.makeText(requireContext(), "Registered successfully!", Toast.LENGTH_SHORT).show()
//
//                                    // Insert locally as well (optional)
//                                    dbHelper.insertUser(name, surname, number, email, country, username, password)
//
//                                    // Navigate to next fragment
//                                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
//                                }
//                                .addOnFailureListener {
//                                    Toast.makeText(requireContext(), "Failed to save user data: ${it.message}", Toast.LENGTH_SHORT).show()
//                                }
//                        }
//                    } else {
//                        Toast.makeText(requireContext(), "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//    }
//}







//package com.example.prog7314outabout
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.FirebaseApp
//
//class RegisterFragment : Fragment() {
//
//    private lateinit var nameEditText: EditText
//    private lateinit var surnameEditText: EditText
//    private lateinit var numberEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var countryEditText: EditText
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var registerButton: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        FirebaseApp.initializeApp(requireContext())
//        return inflater.inflate(R.layout.fragment_register, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Link UI components
//        nameEditText = view.findViewById(R.id.name_txtbox)
//        surnameEditText = view.findViewById(R.id.surname_txtbox)
//        numberEditText = view.findViewById(R.id.number_txtbox)
//        emailEditText = view.findViewById(R.id.email_txtbox)
//        countryEditText = view.findViewById(R.id.country_txtbox)
//        usernameEditText = view.findViewById(R.id.usernameInput)
//        passwordEditText = view.findViewById(R.id.passwordInput)
//        registerButton = view.findViewById(R.id.profileButton)
//
//        val dbHelper = OutnAboutDBHelper(requireContext())
//        val db = FirebaseFirestore.getInstance()
//
//        registerButton.setOnClickListener {
//            val name = nameEditText.text.toString().trim()
//            val surname = surnameEditText.text.toString().trim()
//            val number = numberEditText.text.toString().trim()
//            val email = emailEditText.text.toString().trim()
//            val country = countryEditText.text.toString().trim()
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString()
//
//            // Validate fields
//            if (name.isEmpty() || surname.isEmpty() || number.isEmpty() ||
//                email.isEmpty() || country.isEmpty() || username.isEmpty() || password.isEmpty()
//            ) {
//                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Check if user exists locally
//            if (dbHelper.userExists(username)) {
//                Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Insert user locally
//            val success = dbHelper.insertUser(name, surname, number, email, country, username, password)
//
//            if (success) {
//                val user = hashMapOf(
//                    "name" to name,
//                    "surname" to surname,
//                    "mobile_number" to number,
//                    "email" to email,
//                    "country" to country,
//                    "username" to username,
//                    "password" to password // 🔒 Not recommended in production
//                )
//
//                db.collection("users").document(username)
//                    .set(user)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "Registered successfully and saved to Firebase", Toast.LENGTH_SHORT).show()
//                        // Navigate to next fragment (replace with your ID)
//                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(requireContext(), "Registration saved locally but failed to save to Firebase", Toast.LENGTH_SHORT).show()
//                    }
//
//            } else {
//                Toast.makeText(requireContext(), "Registration failed. Username might already exist.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}

