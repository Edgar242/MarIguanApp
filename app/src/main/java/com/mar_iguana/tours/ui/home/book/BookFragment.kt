package com.mar_iguana.tours.ui.home.book

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mar_iguana.tours.adapters.ViewPagerStepperAdapter
import com.mar_iguana.tours.databinding.FragmentBookBinding
import com.mar_iguana.tours.models.Tour


private const val ARG_PARAM = "tourDetail"

class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val b get() = _binding!!
    private lateinit var tourDetail: Tour

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tourDetail = it.getParcelable<Tour>(ARG_PARAM)!!
        }
//        Toast.makeText(context, tourDetail.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        val view = b.root

        b.textViewTotal.text = tourDetail.price.toString()

        setupStepperView()
        setupViewPager()
        setupButtons()

        return  view
    }

    private fun setupStepperView() {
        b.stepperView.state
            .steps(listOf("First step", "Second step", "Third step"))
            .animationDuration(resources.getInteger(R.integer.config_shortAnimTime))
            .commit()

        b.stepperView.setOnStepClickListener { position ->
            b.viewPagerStepper.setCurrentItem(position, false)
        }
    }

    private fun setupViewPager() {
        b.viewPagerStepper.adapter = ViewPagerStepperAdapter(parentFragmentManager, lifecycle)
        b.viewPagerStepper.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    b.stepperView.go(position, true)
                    setButtonsVisibility(position)
                }
            }
        )
    }

    private fun setupButtons() {
        b.buttonBack.setOnClickListener {
            b.viewPagerStepper.setCurrentItem(
                b.viewPagerStepper.currentItem - 1, false)
        }

        b.buttonNext.setOnClickListener {
            b.viewPagerStepper.setCurrentItem(
                b.viewPagerStepper.currentItem + 1, false)
        }
    }

    private fun setButtonsVisibility(position: Int) {
        when(position){
            0 -> {
                b.buttonBack.visibility = View.INVISIBLE
                b.buttonNext.visibility = View.VISIBLE
            }
            2-> {
                b.buttonBack.visibility = View.VISIBLE
                b.buttonNext.visibility = View.INVISIBLE
            }
            else -> {
                b.buttonBack.visibility = View.VISIBLE
                b.buttonNext.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param tourDetail Tour Detail.
         * @return A new instance of fragment BookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(tourDetail: Tour) =
                BookFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_PARAM, tourDetail)
                    }
                }
    }
}