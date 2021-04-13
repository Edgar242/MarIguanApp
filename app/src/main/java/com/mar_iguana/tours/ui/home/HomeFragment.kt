package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.TourAdapter
import com.mar_iguana.tours.databinding.FragmentHomeBinding
import com.mar_iguana.tours.listeners.TourListener
import com.mar_iguana.tours.models.Tour

class HomeFragment : Fragment(), TourListener {

    private lateinit var homeViewModel: HomeViewModel
    // _binding property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tourAdapter: TourAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        showTours()
        return view
    }

    private fun showTours() {
        val tours = arrayListOf<Tour>(
            Tour(
                100,
                "Real de Catorce y la Media Luna (Fin de semana)",
                2150F,
                "Del 14 al 16 de mayo de 2021",
                4.8F,
                listOf<Int>(
                    R.drawable.real_de_catorce_0,
                    R.drawable.real_de_catorce_1,
                    R.drawable.real_de_catorce_2
                ),
                "Real de Catorce es un lugar espectacular, un pueblo mágico donde nos adentraremos en un mundo que se detuvo en el tiempo de la minería de principios de siglo. En este viaje haremos un tour hacia el pueblo fantasma, daremos un paseo en los famosos willys para conocer el desierto donde podrás observar la planta del peyote y visitar Wirikuta (lugar sagrado para huicholes o wixárikas) ¡No te lo puedes perder! \n" +
                        "\n" +
                        "\n" +
                        "En este tour conocerás a fondo el pueblo de Real de 14 y la media Luna donde pasaremos el fin de semana y viviremos una mágica experiencia.\n" +
                        "\n" +
                        "\n" +
                        "La salida será el viernes 14 de Mayo a las 20:45 horas desde Calle Garibaldi #824 (afuera del Mercado IV Centenario) a un costado de la Capilla de Jesús, que se encuentra a una cuadra de nuestras oficinas ubicadas en Garibaldi #753 esquina con calle Puebla y estaremos de regreso en la perla tapatía el Domingo 16 a las 22:30 hrs.",
                "• Real de 14 (pueblo mágico)\n" +
                        "• Estación 14 (desierto)\n" +
                        "• Mina la Purísima\n" +
                        "•Tunel Ogarrio\n" +
                        "• Pueblo fantasma\n" +
                        "• Cerro del quemado o wirikuta (recorrido opcional)\n" +
                        "• Laguna de la Media Luna",
                "Preventa \$ 8,000 antes del 5 de mayo"
            ),
            Tour(
                100,
                "Cuatrociénegas y zona del Silencio Coahuila 2021(Semana Santa)",
                4999F,
                "Del 31 de Marzo al 04 de Abril 2021",
                4.1F,
                listOf<Int>(R.drawable.cuatro_cienegas_0),
                "La salida será el Miercoles 31 de Marzo a las 17:00 horas desde Calle Garibaldi #824 (afuera del Mercado IV Centenario) a un costado del templo de la Capilla de Jesús, que se encuentra a una cuadra de nuestras oficinas ubicadas en Garibaldi #753 esquina con calle Puebla, Guadalajara Jalisco. Y estaremos de regreso en la Perla Tapatía el Domingo 04 de Abril en el mismo lugar\n" +
                        "\n" +
                        "\n" +
                        "Para estas vacaciones de pascua los invitamos a un viaje muy esperado en una de las regiones mas raras y diversas de México. La Zona del silencio es un lugar en medio del desierto donde abundan mitos y leyendas acerca de fenómenos extraños y paranormales que se han presentado. Visitaremos el desierto, pozas, ríos y manantiales color Turquesa, el desierto Blanco, el pueblo mágico de Cuatro Ciénegas, el puente Ojuela y muchos lugares más.\n" +
                        "\n" +
                        "\n" +
                        "Cuatro ciénegas es el desierto con más agua en el mundo y un lugar sorprendente para la investigación científica, es un ecosistema único donde habitan mas de 70 especies endémicas, Cuatro ciénegas es considerado a nivel global un humedal prioritario para su conservación tanto así que hasta la NASA se ha interesado en el lugar y se le compara con las Islas Galápagos",
                "DURANGO\n" +
                        "• Puente Ojuela\n" +
                        "\n" +
                        "\n" +
                        "COAHUILA:\n" +
                        "• Cuatro Ciénegas (Pueblo Mágico)\n" +
                        "• Poza AZul\n" +
                        "• Río Mezquites\n" +
                        "• Dunas de Yeso\n" +
                        "• Minas de Mármol\n" +
                        "• Cañón\n" +
                        "• Casas vitivinícolas\n" +
                        "\n" +
                        "\n" +
                        "Zona del silencio:\n" +
                        "• Cráter de meteorito\n" +
                        "• Dunas del desierto\n" +
                        "• Pinturas rupestres\n" +
                        "• tour por el Desierto",
                "Preventa \$ 4,500 antes del 1 de marzo"
            ),
            Tour(
                100,
                "Barrancas del Cobre (Chihuahua) Dic 2021",
                8500F,
                "Del 25 de Dec al 31 de Dec, 2021",
                4.5F,
                listOf<Int>(R.drawable.barrancas_del_cobre_0),
                "Para éstas vacaciones de diciembre los invitamos a un viaje muy esperado, visitaremos una de las regiones más bellas de México:\n" +
                        "Las Barrancas del Cobre en Chihuahua, es el sistema de barrancas más grande del mundo y una maravilla de México. Además realizaremos un viaje en el famoso TREN CHEPE y conocerás un poco más a cerca de la cultura Rarámuri o Tarahumara y ¿por qué no? ¡también mucha diversión!\n" +
                        "\n" +
                        "\n" +
                        "La salida será el sábado 25 de diciembre a las 16:30 HRS desde Calle Garibaldi #824 (afuera del Mercado IV Centenario) a un costado de la Capilla de Jesús, que se encuentra a una cuadra de nuestras oficinas ubicadas en Garibaldi #753 esquina con calle Puebla y estaremos de regreso en la perla tapatía el viernes 31 a las 10:00 HRS.",
                "• Ciudad de Chihuahua\n" +
                        "• Cd. de Cuauhtémoc y los Menonitas\n" +
                        "• Parque nacional Cascada de Basaseachi\n" +
                        "• Creel (Lago Arareko, Cuevas Tarahumaras, Cascada Cusarare, Valle de los Hongos y las Ranas y Las Misiones)\n" +
                        "• Barrancas del Cobre (Divisadero y Piedra Volada)\n" +
                        "• Parque de Aventuras Barrancas del cobre (actividades opcionales con costo extra)",
                "Preventa \$ 8,000 antes del 1 de diciembre"
            ),
            Tour(
                100,
                "Chiapas Pascua 2021",
                5700F,
                "Del 04 al 11 de Abril 2021",
                4.9F,
                listOf<Int>(R.drawable.chiapas_0),
                "Hola amigos los invitamos a vivir esta mágica experiencia. Vamos a realizar una travesía de 7 días por el espectacular estado de Chiapas, si no tuviste la oportunidad de visitar este lugar en otras ocasiones ¡no lo piensen más! estaremos de viaje toda la semana de pascua para que vayas planeando tus vacaciones.\n" +
                        "\n" +
                        "\n" +
                        "La salida será el domingo 04 de abril a las 23:30 horas desde Calle Garibaldi #824 (afuera del Mercado IV Centenario) a un costado del templo de la Capilla de Jesús, que se encuentra a una cuadra de nuestras oficinas ubicadas en Garibaldi #753 esquina con calle Puebla, Guadalajara Jalisco y estaremos de regreso en la Perla Tapatía el domingo 11 de abril en el mismo lugar alrededor de las 23:00 HRS aprox.",
                "VERACRUZ:\n" +
                        "• Orizaba (Pueblo mágico)\n" +
                        "\n" +
                        "\n" +
                        "HIDALGO\n" +
                        "• Mineral del chico (Pueblo mágico)\n" +
                        "\n" +
                        "\n" +
                        "CHIAPAS:\n" +
                        "• Cañón del Sumidero\n" +
                        "• Chiapa de Corzo\n" +
                        "• San Cristóbal de las Casas\n" +
                        "• Lagunas de Montebello\n" +
                        "• Cascadas del Chiflón\n" +
                        "• Zinacantán\n" +
                        "• San Juan Chamula\n" +
                        "• Zona arqueológica de Toniná\n" +
                        "• Cascadas de Agua Azul\n" +
                        "• Cascada de Misol-Ha\n" +
                        "• Zona arqueológica de Palenque",
                "Preventa $ 5,300 antes de 1 de abril"
            ),
        )

        //create adapter and set listener click on cardview
        tourAdapter = TourAdapter(tours)
        tourAdapter.setTourListener(this)
        // Set adapter
        binding.recyclerViewTours.adapter = tourAdapter
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        binding.recyclerViewTours.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Actions to do when click over cardView
    override fun onClickShowTourDetail(tour: Tour) {
        val tourDetailFragment = TourDetailFragment()

        //Set tour data to detail fragment
        val bundleTour = Bundle()
        bundleTour.putParcelable("dataTour",tour)
        tourDetailFragment.arguments = bundleTour

        //Go to detail fragment
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, tourDetailFragment)
            addToBackStack(null)
            commit()
        }
    }

}