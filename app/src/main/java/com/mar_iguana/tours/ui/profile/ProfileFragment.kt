package com.mar_iguana.tours.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    //private lateinit var profileViewModel: ProfileViewModel
    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var name : Boolean = false
    var lastname : Boolean = false
    var email : Boolean = false
    var phoneNumber : Boolean = false
    var gender : Boolean = false
    var genderValue : String = "N"
    var correctPassword : Boolean = false
    var pass : String = ""
    var repeat_pass : String = ""

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

        fun comparePassword(pass : String, repeatPass : String){
            if(pass == repeatPass){
                correctPassword = true
                binding.etRepeatPassword.error = null
            }else{
                correctPassword = false
                binding.etRepeatPassword.error = getString(R.string.passwords_do_not_match)
            }
        }

        fun validation(){
            binding.button.isEnabled = name and lastname and email and phoneNumber and gender and correctPassword
        }

        //Check name
        binding.etNameU.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etNameU.text.toString().length>2){
                    name = true
                }else{
                    name = false
                    binding.etNameU.error = getString(R.string.invalid_name)
                }
                validation()
            }
        })

        //Check lastname
        binding.etLastName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etLastName.text.toString().length>2){
                    lastname = true
                }else{
                    lastname = false
                    binding.etLastName.error = getString(R.string.invalid_last_name)
                }
                validation()
            }
        })

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

        //Check phone number
        binding.etCPNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etCPNumber.text.toString().length> 9){
                    phoneNumber = true
                }else{
                    phoneNumber = false
                    binding.etCPNumber.error = getString(R.string.invalid_phone_number)
                }
                validation()
            }
        })

        //Radio group
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            gender = true
            genderValue = getText(checkedId).toString()
            //Toast.makeText(activity,genderValue,Toast.LENGTH_SHORT).show()
            validation()
        }

        //Check password
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etPassword.text.toString().length<6){
                    binding.etPassword.error = getString(R.string.too_short_password)
                }
                comparePassword(binding.etPassword.text.toString(), binding.etRepeatPassword.text.toString())
                validation()
            }
        })

        //Check repeat password
        binding.etRepeatPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etRepeatPassword.text.toString().length<6){
                    binding.etRepeatPassword.error = getString(R.string.too_short_password)
                }
                comparePassword(binding.etPassword.text.toString(), binding.etRepeatPassword.text.toString())
                validation()
            }
        })

    }

}