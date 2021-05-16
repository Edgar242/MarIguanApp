package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    //private lateinit var profileViewModel: ProfileViewModel
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
       _binding = FragmentProfileBinding.inflate(inflater,container,false)

        //Connection with database
        var ref = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()

        val user: FirebaseUser? = auth.currentUser

        //Getting user data from firebase realtime database
        val getData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //finding user in database
                var userData = dataSnapshot.children.find{
                    it.key == user?.uid
                }

                //setting view data from user data
                binding.userProfileName.setText(userData?.child("name")?.getValue().toString())
                binding.userLastName.setText(userData?.child("lastname")?.getValue().toString())
                binding.userPhone.setText(userData?.child("phone_number")?.getValue().toString())
                binding.userEmail.setText(user?.email)

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)
        
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val loginFragment = LoginFragment()
            val bundleLogin = Bundle()
            loginFragment.arguments = bundleLogin

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, loginFragment)
                addToBackStack(null)
                commit()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(){
                    task ->
                    if(task.isComplete){
                        Toast.makeText(activity,R.string.verification_e_sent,Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(activity,R.string.an_error_ocurred,Toast.LENGTH_LONG).show()
                    }

                }
    }
}