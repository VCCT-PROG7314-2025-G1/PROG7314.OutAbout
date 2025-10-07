package com.example.prog7314outabout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var googleLoginButton: Button
    private lateinit var biometricsButton: Button
    private lateinit var forgotPasswordText: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var dbHelper: OutnAboutDBHelper
    private lateinit var firestore: FirebaseFirestore
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.username_txtbox)
        passwordEditText = view.findViewById(R.id.password_txtbox)
        loginButton = view.findViewById(R.id.loginButton)
        googleLoginButton = view.findViewById(R.id.btnGoogleSignIn)
        biometricsButton = view.findViewById(R.id.biometricsButton)
        forgotPasswordText = view.findViewById(R.id.forgotPassword)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        dbHelper = OutnAboutDBHelper(requireContext())

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Local login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // First check local SQLite database
            if (dbHelper.validateUser(username, password)) {
                // after verifying login success
                val sharedPref = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("loggedInUsername", username) // save the username
                    apply()
                }
                Toast.makeText(requireContext(), "Local login successful!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                // Optional: If you also want Firebase login using email
                Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        // Google login
        googleLoginButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                googleSignInLauncher.launch(googleSignInClient.signInIntent)
            }
        }

        // Biometric placeholder
        biometricsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Biometric login coming soon", Toast.LENGTH_SHORT).show()
        }

        // Forgot password
        forgotPasswordText.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Enter your email first", Toast.LENGTH_SHORT).show()
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Reset email sent", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    // Google Sign-In Launcher
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account == null) {
                    Toast.makeText(requireContext(), "Google account is null", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuthWithGoogle(account.idToken!!, account)
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Google Sign-In failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Google Sign-In cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                // Add new Google users to Firestore automatically
                val userRef = firestore.collection("users").document(auth.currentUser!!.uid)
                userRef.get().addOnSuccessListener { doc ->
                    if (!doc.exists()) {
                        val userData = hashMapOf(
                            "name" to (account.displayName ?: ""),
                            "email" to (account.email ?: ""),
                            "profilePic" to (account.photoUrl?.toString() ?: "")
                        )
                        userRef.set(userData)
                    }
                }
                Toast.makeText(requireContext(), "Google Sign-In successful!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Firebase auth failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}



//doesnt work-only sso
//package com.example.prog7314outabout
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.prog7314outabout.databinding.FragmentLoginBinding
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//
//class LoginFragment : Fragment() {
//
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var dbHelper: OutnAboutDBHelper
//
//    private val RC_SIGN_IN = 1001
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize dbHelper
//        dbHelper = OutnAboutDBHelper(requireContext())
//
//        // Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//        // Set click listener for Google sign-in button
//        binding.btnGoogleSignIn.setOnClickListener {
//            signInWithGoogle()
//        }
//    }
//
//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            val email = account?.email
//
//            if (email != null) {
//                if (dbHelper.emailExists(email)) {
//                    // Email exists in SQLite DB → navigate to HomeFragment
//                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                } else {
//                    Toast.makeText(requireContext(), "Email not registered", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(requireContext(), "Google Sign-In failed", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: ApiException) {
//            Log.w("LoginFragment", "Google sign in failed", e)
//            Toast.makeText(requireContext(), "Sign-In error: ${e.statusCode}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}


////works 1/10/2025- SSO doesnt work 100%
//package com.example.prog7314outabout
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.firestore.FirebaseFirestore
//
//class LoginFragment : Fragment() {
//
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var loginButton: Button
//    private lateinit var googleLoginButton: Button
//    private lateinit var biometricsButton: Button
//    private lateinit var forgotPasswordText: TextView
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var dbHelper: OutnAboutDBHelper
//    private lateinit var firestore: FirebaseFirestore
//    private lateinit var googleSignInClient: GoogleSignInClient
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        usernameEditText = view.findViewById(R.id.username_txtbox)
//        passwordEditText = view.findViewById(R.id.password_txtbox)
//        loginButton = view.findViewById(R.id.loginButton)
//        googleLoginButton = view.findViewById(R.id.btnGoogleSignIn)
//        biometricsButton = view.findViewById(R.id.biometricsButton)
//        forgotPasswordText = view.findViewById(R.id.forgotPassword)
//
//        auth = FirebaseAuth.getInstance()
//        firestore = FirebaseFirestore.getInstance()
//        dbHelper = OutnAboutDBHelper(requireContext())
//
//        // Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id)) // Make sure you have this in strings.xml
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//        // Local login
//        loginButton.setOnClickListener {
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (dbHelper.validateUser(username, password)) {
//                Toast.makeText(requireContext(), "Local login successful!", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            } else {
//                auth.signInWithEmailAndPassword(username, password)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "Firebase login successful!", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(requireContext(), "Invalid credentials: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//
//        // Google login
//        googleLoginButton.setOnClickListener {
//            googleSignInLauncher.launch(googleSignInClient.signInIntent)
//        }
//
//        // Biometric placeholder
//        biometricsButton.setOnClickListener {
//            Toast.makeText(requireContext(), "Biometric login coming soon", Toast.LENGTH_SHORT).show()
//        }
//
//        // Forgot password
//        forgotPasswordText.setOnClickListener {
//            val email = usernameEditText.text.toString().trim()
//            if (email.isEmpty()) {
//                Toast.makeText(requireContext(), "Enter your email first", Toast.LENGTH_SHORT).show()
//            } else {
//                auth.sendPasswordResetEmail(email)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "Reset email sent", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//    }
//
//    // Google Sign-In Launcher
//    private val googleSignInLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            try {
//                val account: GoogleSignInAccount? = task.getResult(Exception::class.java)
//                account?.idToken?.let { firebaseAuthWithGoogle(it) }
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnSuccessListener {
//                Toast.makeText(requireContext(), "Google Sign-In successful!", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            }
//            .addOnFailureListener {
//                Toast.makeText(requireContext(), "Google Sign-In failed: ${it.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//}



//package com.example.prog7314outabout
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//class LoginFragment : Fragment() {
//
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var loginButton: Button
//    private lateinit var googleLoginButton: Button
//    private lateinit var biometricsButton: Button
//    private lateinit var forgotPasswordText: TextView
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var dbHelper: OutnAboutDBHelper
//    private lateinit var firestore: FirebaseFirestore
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Link UI
//        usernameEditText = view.findViewById(R.id.username_txtbox)
//        passwordEditText = view.findViewById(R.id.password_txtbox)
//        loginButton = view.findViewById(R.id.loginButton)
//        googleLoginButton = view.findViewById(R.id.googleLoginButton)
//        biometricsButton = view.findViewById(R.id.biometricsButton)
//        forgotPasswordText = view.findViewById(R.id.forgotPassword)
//
//        // Init Firebase + Local DB
//        auth = FirebaseAuth.getInstance()
//        firestore = FirebaseFirestore.getInstance()
//        val dbHelper = OutnAboutDBHelper(requireContext())
//
//        loginButton.setOnClickListener {
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // First try local DB login with username + password
//            if (dbHelper.validateUser(username, password)) {
//                Toast.makeText(requireContext(), "Local login successful!", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            } else {
//                // If local fails, assume user meant email + password (Firebase)
//                auth.signInWithEmailAndPassword(username, password)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "Firebase login successful!", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(requireContext(), "Invalid credentials: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//
//
//        // "google" button → here it just re-uses Firebase Email Authentication
//        googleLoginButton.setOnClickListener {
//            val email = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(requireContext(), "Enter email and password to continue", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnSuccessListener {
//                    Toast.makeText(requireContext(), "SSO Login successful", Toast.LENGTH_SHORT).show()
//                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(), "SSO Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
//                }
//        }
//
//        //  Biometrics placeholder
//        biometricsButton.setOnClickListener {
//            Toast.makeText(requireContext(), "Biometric login coming soon", Toast.LENGTH_SHORT).show()
//        }
//
//        //  Forgot password
//        forgotPasswordText.setOnClickListener {
//            val email = usernameEditText.text.toString().trim()
//            if (email.isEmpty()) {
//                Toast.makeText(requireContext(), "Enter your email first", Toast.LENGTH_SHORT).show()
//            } else {
//                auth.sendPasswordResetEmail(email)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "Reset email sent", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//    }
//}
