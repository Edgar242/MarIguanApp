package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
    var name = ""
    var lastname = ""
    var phone_number = ""
    var email = ""

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

                name = userData?.child("name")?.getValue().toString()
                lastname = userData?.child("lastname")?.getValue().toString()
                phone_number = userData?.child("phone_number")?.getValue().toString()
                email = user!!.email

                //setting view data from user data
                binding.userProfileName.setText(name)
                binding.userLastName.setText(lastname)
                binding.userPhone.setText(phone_number)
                binding.userEmail.setText(email)

                var trips = userData?.child("viajes")?.children
                var tripsHistory = StringBuilder()
                if(trips != null){
                    for(i in trips){
                        var title = i.child("title").getValue().toString()
                        var status = i.child("status").getValue().toString()
                        tripsHistory.append("${title} - ${status}\r\n")
                    }
                    binding.textViewHistory.setText(tripsHistory)
                }
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

        binding.editBtn.setOnClickListener{
            val editProfileFragment = EditProfileFragment()
            val bundleLogin = Bundle()
            bundleLogin.putString("name",name)
            bundleLogin.putString("lastname",lastname)
            bundleLogin.putString("phone_number",phone_number)
            bundleLogin.putString("email",email)
            editProfileFragment.arguments = bundleLogin

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, editProfileFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.textViewHistory.movementMethod = ScrollingMovementMethod()

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