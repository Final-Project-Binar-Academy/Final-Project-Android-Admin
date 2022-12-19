package com.example.final_project_android_admin.ui.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentAddAirportBinding
import com.example.final_project_android_admin.viewmodel.AirportViewModel

class AddAirportFragment : Fragment() {
    private var _binding: FragmentAddAirportBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAddAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        airportViewModel.airportResult.observe(viewLifecycleOwner) {
//            when (it) {
//                is BaseResponse.Success -> {
//                    processCreateAirport(it.data)
//                }
//                is BaseResponse.Error -> {
//                    processError(it.msg)
//                }
//                else -> {}
//            }
//        }

        binding.btnSave.setOnClickListener{
            val airport_name = binding.txtAirport.text
            val city = binding.txtCity.text
            val city_code = binding.txtCityCode.text
//            airportViewModel.createAirport(airport_name, city, city_code)
            findNavController().navigate(R.id.action_addAirportFragment_to_airportFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun processError(msg: String?) {
        Toast.makeText(
            requireContext(),
            "Error:$msg",
            Toast.LENGTH_SHORT
        ).show()
    }

//    private fun processCreateAirport(data: AirportResponse?) {
//        Toast.makeText(
//            requireContext(),
//            "Success:" + data?.message,
//            Toast.LENGTH_SHORT
//        ).show()
//    }
}