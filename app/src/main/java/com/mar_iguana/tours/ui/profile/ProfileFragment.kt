package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mar_iguana.tours.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    //private lateinit var profileViewModel: ProfileViewModel
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
       _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Connection with database
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        //Getting child "User" (equivalent to table "User" in SQL)
        dbReference = database.reference.child("User")

        val user: FirebaseUser? = auth.currentUser
        //getting user with user id
        val userBD = dbReference.child(user?.uid.toString())

        binding.userName.text = user?.uid.toString()
    }

}