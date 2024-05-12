package com.example.foodrecipe.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.foodrecipe.MainActivity2
import com.example.foodrecipe.R
import com.google.android.play.core.integrity.g
import com.google.firebase.auth.FirebaseAuth

@Suppress("UNREACHABLE_CODE")
class Signup : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email : EditText
    private lateinit var passaword: EditText
    private lateinit var confirmpass: EditText
    private lateinit var button: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =inflater.inflate(R.layout.fragment_signup, container, false)
        val firebaseAuth =FirebaseAuth.getInstance()
        passaword = view.findViewById(R.id.signupEditTextpass)
        confirmpass = view.findViewById(R.id.signupEditTextConfirmpass)
        email = view.findViewById(R.id.signupEditTextEmail)
        button =view.findViewById(R.id.SignupButton)

        val fragment: Fragment = SignIn()
        button.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                if (email.text.toString().isNotEmpty()
                    && passaword.text.toString().isNotEmpty()
                    && confirmpass.text.toString().isNotEmpty()
                ) {
                    if (passaword.text.toString() != confirmpass.text.toString()) {
                        Toast.makeText(
                            requireContext(),
                            "Password doesn't match",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        firebaseAuth.createUserWithEmailAndPassword(
                            email.text.toString(),
                            passaword.text.toString()
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Registered Succesfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(requireContext(), MainActivity2::class.java)
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
                else{
                    Toast.makeText(requireContext(), "Empty Field not allowed", Toast.LENGTH_SHORT).show()
            }
            }
        }
        return view
    }
}