package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var email : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser.uid.toString() != "2WHaYi5APmh8jvfs4VceGLVgQkI3"){
            //If user is logged open user profile instead of login fragment
            goToProfile()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun validation(){
            binding.buttonLogin.isEnabled = email
        }

        //Check email
        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()){
                    email = true
                }else{
                    email = false
                    binding.etEmail.error = getString(R.string.invalid_email)
                }
                validation()
            }
        })

        binding.buttonLogin.setOnClickListener {
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            goToProfile()
                        }else{
                            binding.AuthError.visibility = View.VISIBLE
                            Toast.makeText(activity,it.exception?.message,Toast.LENGTH_LONG*2).show()
                        }
                    }
        }

        //go to register
        fun goToRegister(){

        }

        binding.btnGoRegister.setOnClickListener {
            val registerFragment = RegisterFragment()
            val bundleLogin = Bundle()
            registerFragment.arguments = bundleLogin

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, registerFragment)
                addToBackStack(null)
                commit()
            }
        }

    }

    fun goToProfile(){
        val profileFragment = ProfileFragment()
        val bundleProfile = Bundle()
        profileFragment.arguments = bundleProfile

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, profileFragment)
            addToBackStack(null)
            commit()
        }
    }

}