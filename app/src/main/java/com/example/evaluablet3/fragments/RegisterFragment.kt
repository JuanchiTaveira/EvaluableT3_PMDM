package com.example.evaluablet3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.evaluablet3.R
import com.example.evaluablet3.databinding.FragmentRegisterBinding
import com.example.evaluablet3.model.User
import com.example.evaluablet3.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://evaluablet3-pmdm-default-rtdb.europe-west1.firebasedatabase.app/")

        binding.btnRegister.setOnClickListener {

            Utils.hideKeyboard(this, view)

            val email = binding.editTextEmail.text.toString()
            val name = binding.editTextName.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isBlank() || password.isBlank() || name.isBlank())
                return@setOnClickListener

            val newUser = User(email, name)

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    database.getReference("users").child(auth.currentUser!!.uid).setValue(newUser).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Snackbar.make(binding.root, "Usuario creado con éxito", Snackbar.LENGTH_SHORT).show()

                            val bundle = Bundle()
                            bundle.putString("email", email)
                            bundle.putString("password", password)

                            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment, bundle)
                        } else {
                            Snackbar.make(binding.root, "Error al registrarse", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Snackbar.make(binding.root, "Error al registrarse", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}