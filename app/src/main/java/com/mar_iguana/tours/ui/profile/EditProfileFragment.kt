package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentEditProfileBinding
import com.mar_iguana.tours.databinding.FragmentTourDetailBinding
import com.mar_iguana.tours.models.Tour


class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val userName =  getArguments()?.getString("name")
        val userLastName = getArguments()?.getString("lastname")
        val userPhoneNumber = getArguments()?.getString("phone_number")
        val userEmail = getArguments()?.getString("email")

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.editTextTextPersonName2.setText(userName)
        binding.editTextLastName.setText(userLastName)
        binding.editTextPhoneNumber.setText(userPhoneNumber)
        binding.editTextEmail.setText(userEmail)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        binding.buttonSave.setOnClickListener {
            val user: FirebaseUser? = auth.currentUser
            val userBD = dbReference.child(user?.uid.toString())
            userBD.child("name").setValue(binding.editTextTextPersonName2.text.toString())
            userBD.child("lastname").setValue(binding.editTextLastName.text.toString())
            userBD.child("phone_number").setValue(binding.editTextPhoneNumber.text.toString())

            val profileFragment = ProfileFragment()
            val bundleProfile = Bundle()
            profileFragment.arguments = bundleProfile

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, profileFragment)
                addToBackStack(null)
                commit()
            }
        }

        return view
    }

}