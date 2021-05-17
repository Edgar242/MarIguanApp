package com.mar_iguana.tours.ui.home.booking

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentBookStepThreeBinding
import com.google.firebase.auth.FirebaseUser


class BookStepThreeFragment : Fragment() {
    private var _binding : FragmentBookStepThreeBinding? = null
    private val b get() = _binding!!
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
            val tourDB = tipsDB.child("${tour.id}")
            tourDB.child("title").setValue(tour.title)
            tourDB.child("status").setValue("Verificando.")
        }

        //==========================================================================================

        val paymentMethods = resources.getStringArray(R.array.payment_type)
        val adapter = ArrayAdapter<String>(requireContext(),
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
}
