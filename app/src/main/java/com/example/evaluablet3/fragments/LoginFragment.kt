package com.example.evaluablet3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.evaluablet3.R
import com.example.evaluablet3.databinding.FragmentLoginBinding
import com.example.evaluablet3.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var email : String? = null
    private var password : String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        email = arguments?.getString("email")
        password = arguments?.getString("password")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextEmail.setText(email)
        binding.editTextPassword.setText(password)

        binding.btnLogin.setOnClickListener {

            Utils.hideKeyboard(this, view)

            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isBlank() || password.isBlank())
                return@setOnClickListener

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //TODO: move to menuActivity
                    } else {
                        Snackbar.make(binding.root, "Correo o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}