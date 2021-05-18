package com.mar_iguana.tours.ui.home.booking

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mar_iguana.tours.BuildConfig
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentBookStepThreeBinding
import java.io.File
import java.io.IOException


private const val CAMERA_REQUEST_PERMISSION_CODE = 1
private const val CAMERA_REQUEST_CODE = 42
private const val OPEN_GALLERY_REQUEST_CODE = 33
private const val READ_EXTERNAL_PERMISSION_CODE = 34

class BookStepThreeFragment : Fragment() {
    private var _binding : FragmentBookStepThreeBinding? = null
    private val b get() = _binding!!
    private lateinit var currentPhotoPath: String

    private lateinit var bookFragment: BookFragment

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBookStepThreeBinding.inflate(inflater, container, false)
        val view = b.root

        // Uploading purchased trip to firebase ====================================================

        //Connection with firebase
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        //getting tour data
        bookFragment = parentFragment as BookFragment
        val tour = bookFragment.tourDetail

        // getting user from firebase databse
        val user: FirebaseUser? = auth.currentUser
        val tipsDB = dbReference.child(user?.uid.toString()).child("viajes")

        //uploading trip on clicking buttonUpload
        b.buttonUpload.setOnClickListener {
            showUploadOptions()
            val tourDB = tipsDB.child("${tour.id}")
            tourDB.child("title").setValue(tour.title)
            tourDB.child("status").setValue("Verificando.")
        }

        //==========================================================================================

        val paymentMethods = resources.getStringArray(R.array.payment_type)
        val adapter = ArrayAdapter(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            paymentMethods)
        b.spinner.adapter = adapter

        b.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {
                        b.textViewPaymentDescription.text = ""
                    }
                    1 -> { // Bank transfer
                        b.textViewPaymentDescription.text =
                            Html.fromHtml("<p>Favor de realizar su pago mediante alguna de las siguientes opciones:</p>" +
                                    "<ul>\n" +
                                    "  <li> Banco Santander Serfin Cuenta: <h5><b>60556378138</b></h5></li>\n" +
                                    "  <li> CLABE (Clave Interbancaria): <br><h5><b>014320605563781380</b></h5></li>\n" +
                                    "  <li> Tarjeta para deposito en OXXO: <br><h5><b>5579 0700 3238 1124</b></h5></li>\n" +
                                    "</ul>")

//                            "Favor de realizar su pago mediante alguna de las siguientes opciones:\n\n" +
//                                "• Banco Santander Serfin Cta.: 60556378138\n\n" +
//                                "• CLABE (Clave Interbancaria): 014320605563781380\n\n" +
//                                "• Tarjeta para deposito en OXXO: 5579 0700 3238 1124\n\n" +
//                                "Nota: Conserve su ticket en caso de futuras aclaraciones."
                    }
                    2 -> { // Cash
                        b.textViewPaymentDescription.text = "Dirigete a nuestras instalaciones de lunes a viernes de 12 a 8 pm y sábados de 10 a 2 pm.\n" +
                                "Dirección oficinas: Garibaldi #753 esquina con calle Puebla, Guadalajara, Jalisco\n" +
                                "Telefonos: 20159141, cel/whatsapp 3319077565 / 3311757525"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                b.textViewPaymentDescription.text = ""
            }
        }

        return view
    }

    private fun showUploadOptions() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Upload proof of payment")
            setItems(arrayOf("Take a picture", "Choose from gallery")) { _, which ->
                when(which) {
                    0 -> {
                        openCamera()
                    }
                    1 -> {
                        openGallery()
                    }
                }
            }
            setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(requireContext(), "You must choose an option to upload your proof of payment", Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    private fun openCamera() {
        if (checkPermissions(Manifest.permission.CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_PERMISSION_CODE)
        }
    }


    private fun checkPermissions(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(requireContext(), "Permission was denied", Toast.LENGTH_LONG).show()
            }
        }

        if (requestCode == READ_EXTERNAL_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchGalleryIntent()
            } else {
                Toast.makeText(requireContext(), "Permission was denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Create the File where the photo should go
            val photoFile: File? = try {
                val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                File.createTempFile("photo", ".jpg", storageDir).apply {
                    // Save a file: path for use with ACTION_VIEW intents
                    currentPhotoPath = absolutePath
                }
            } catch (ex: IOException) {
                // Error occurred while creating the File
                return
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun openGallery() {
        if (checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            dispatchGalleryIntent()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_PERMISSION_CODE)
        }
    }

    private fun dispatchGalleryIntent() {
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            startActivityForResult(this, OPEN_GALLERY_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }
}
