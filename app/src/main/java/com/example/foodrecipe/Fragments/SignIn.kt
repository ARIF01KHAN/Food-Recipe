package com.example.foodrecipe.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.foodrecipe.MainActivity2
import com.example.foodrecipe.R
import com.google.firebase.auth.FirebaseAuth

class SignIn : Fragment() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var button: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        email= view.findViewById(R.id.signInEditTextEmail)
        password = view.findViewById(R.id.signInEditTextpass)
        button= view.findViewById(R.id.SignInButton)

        button.setOnClickListener {
            val EmailText = email.text.toString()
            val PasswordText = password.text.toString()
            if (EmailText.isNotEmpty() && PasswordText.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(EmailText,PasswordText).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(requireContext(), MainActivity2::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(),"Please Enter data", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}